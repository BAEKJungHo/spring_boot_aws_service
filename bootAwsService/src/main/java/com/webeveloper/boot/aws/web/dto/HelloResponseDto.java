package com.webeveloper.boot.aws.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BAEKJH
 * @since 2020-04-24
 * @discription HelloResponseDto
 */
@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

    private final String name;
    private final int amount;

}
