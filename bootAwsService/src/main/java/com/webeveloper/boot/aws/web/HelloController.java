package com.webeveloper.boot.aws.web;

import com.webeveloper.boot.aws.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BAEKJH
 * @since 2020-04-23
 * @discription HelloController
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * @RestController : JSON 을 반환하는 컨트롤러로 만들어준다.
     * @return
     */
    @GetMapping
    public String hello() {
        return "hello";
    }

    @GetMapping("/dto")
    public HelloResponseDto helloDto(
            @RequestParam("name") String name,
            @RequestParam("amount") int amount
    ) {
        return new HelloResponseDto(name, amount);
    }

}
