package com.webeveloper.boot.aws.web.dto;

import com.webeveloper.boot.aws.domain.posts.Posts;
import lombok.Getter;

/**
 * @author BAEKJH
 * @since 2020-04-25
 * @discription PostsApiController
 */
@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    /**
     * PostsResponseDto 는 Entity 의 필드 중 일부만 사용하므로 생성자로 Entity 를 받아 필드에 값 넣기
     * @param entity
     */
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }

}
