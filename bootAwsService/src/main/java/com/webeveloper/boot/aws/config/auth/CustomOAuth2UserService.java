package com.webeveloper.boot.aws.config.auth;

import com.webeveloper.boot.aws.config.auth.dto.OAuthAttributes;
import com.webeveloper.boot.aws.config.auth.dto.SessionUser;
import com.webeveloper.boot.aws.domain.user.User;
import com.webeveloper.boot.aws.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * 구글 로그인 이후 가져온 사용자의 정보들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /*
         * 현재 로그인 진행 중인 서비스를 구분하는 코드
         * 구글만 사용하는 경우에는 불필요한 값이지만, 나중에 네이버, 카카오 등 로그인 연동하게 되는경우 필요함
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        /*
         * OAuth2 로그인 진행 시 키가 되는 필드값을 이야기한다. 즉, Primary Key 와 같은 의미
         * 구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 지원하지 않는다.
         * 구글의 기본 코드는 "sub" 이다.
         * 이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용된다.
         */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        /*
         * OAuth2UserService 를 통해 가져온 OAuth2User 의 attribute 를 담을 클래스.
         * 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용
         */
        OAuthAttributes authAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(authAttributes);

        /*
         * SessionuUser
         * 세션에 "인증된 사용자 정보" 를 저장하기 위한 Dto 클래스
         * 여기서 사용자 정보를 가지고 있는 User 클래스를 사용하면 안되는 이유는 아래와 같다.
         * - User 클래스를 사용할 경우 다음과 같은 에러 발생 "Failed to Convert form type [java.lang.Object] to type [byte[]] for value ...
         * - 이는 세션에 저장하기 위해 User 클래스를 세션에 저장하려고하니, User 클래스에 직렬화를 구현하지 않았다는 의미의 에러이다.
         * - 그렇다고 User 클래스에 직렬화를 구현해서 사용하는것은 추천하지 않는다.
         * - User 클래스는 엔티티 클래스 이므로, 언제 다른 엔티티와 관계가 형성될지 모르기 때문이다.
         * - 예를 들어, @OneToMany, @ManyToMany 등 자식 엔티티를 가지고 있다면, 직렬화 대상에 자식까지 포함되니, 성능 이슈, 부수효과가 발생할 확률이 높다.
         * - 따라서, 직렬화 기능을 가진 세션 Dto 를 하나 추가로 만드는 것이 이후 운영 및 유지보수에 도움이 많이 된다.
         */
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                authAttributes.getAttributes(),
                authAttributes.getNameAttributeKey()
        );
    }

    /**
     * 구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능도 같이 구현
     * 사용자의 이름이나 프로필 사진이 변경되면 User 엔티티에도 반영
     * @param authAttributes
     * @return
     */
    private User saveOrUpdate(OAuthAttributes authAttributes) {
        User user = userRepository.findByEmail(authAttributes.getEmail())
                .map(entity -> entity.update(authAttributes.getName(), authAttributes.getPicture()))
                .orElse(authAttributes.toEntity());
        return userRepository.save(user);
    }

}
