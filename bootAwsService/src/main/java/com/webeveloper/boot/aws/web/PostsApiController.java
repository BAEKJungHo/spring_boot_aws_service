package com.webeveloper.boot.aws.web;

import com.webeveloper.boot.aws.service.posts.PostsService;
import com.webeveloper.boot.aws.web.dto.PostsResponseDto;
import com.webeveloper.boot.aws.web.dto.PostsSaveRequestDto;
import com.webeveloper.boot.aws.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author BAEKJH
 * @since 2020-04-25
 * @discription PostsApiController
 */
@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id) {
        return postsService.findById(id);
    }
}
