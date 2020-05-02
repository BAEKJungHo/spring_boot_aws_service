package com.webeveloper.boot.aws.web;

import com.webeveloper.boot.aws.config.auth.LoginUser;
import com.webeveloper.boot.aws.config.auth.dto.SessionUser;
import com.webeveloper.boot.aws.service.posts.PostsService;
import com.webeveloper.boot.aws.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    /**
     * 머스테치를 사용하면 머스테치 스타터 덕분에 앞의 경로와 뒤의 파일 확장자가 자동으로 붙는다.
     * src/main/resources/templates + index + .mustache
     * ViewResolver 가 알아서 처리
     * @return
     */
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        /*
         * CustomOAuth2UserService 에서 로그인 성공 시 세션에 SessionUser 를 저장하도록 구성
         * 따라서, 로그인 성공 시 httpSession.getAttribute("user") 에서 값을 가져올 수 있다.
         *
         * 어노테이션으로 대체
         */
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts", dto);

        return "posts-update";
    }

}
