package com.webeveloper.boot.aws.web;

import com.webeveloper.boot.aws.service.posts.PostsService;
import com.webeveloper.boot.aws.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    /**
     * 머스테치를 사용하면 머스테치 스타터 덕분에 앞의 경로와 뒤의 파일 확장자가 자동으로 붙는다.
     * src/main/resources/templates + index + .mustache
     * ViewResolver 가 알아서 처리
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
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
