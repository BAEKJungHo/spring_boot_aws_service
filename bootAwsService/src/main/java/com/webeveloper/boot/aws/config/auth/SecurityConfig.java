package com.webeveloper.boot.aws.config.auth;

import com.webeveloper.boot.aws.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @EnableWebSecurity
 * - 스프링 시큐리티 설정들을 활성화 시켜준다.
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    /**
     * csrf().disable().headers().frameOptions().disable() : h2-console 화면을 사용하기 위해 해동 옵션들을 disable 한다.
     * authorizeRequests : URL 별 권한 관리를 설정 하는 옵션의 시작점
     * antMatchers : authorizeRequests 가 선언 되어야 사용할 수 있다.
     * anyRequest : 설정된 값들 이외 나머지 URL 을 나타낸다.
     * 여기서는 authenticated() 를 추가하여 나머지 URL 들은 모두 인증된 사용자들에게만 허용하게 한다.
     * logout().logoutSuccessUrl("/") : 로그아웃 기능에 대한 여러 설정의 진입점, 로그아웃 성공 시 해당 주소로 이동
     * oauth2Login : OAuth2 로그인 기능에 대한 여러 설정의 진입점
     * userInfoEndpoint : OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
     * userService : 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
     * 리로스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고 자 하는 기능을 명시할 수 있다.
     *
     * antMatchers("/api/v1/**").hasRole(Role.USER.name()) 해당 경로에 존재하는 기능들에 대해서 권한을 USER 로 설정하는 것이다.
     * name() 메서드는 USER 라는 Enum 이름 그대로 문자열 USER 를 반환한다.
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }

}
