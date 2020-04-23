package com.webeveloper.boot.aws.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BAEKJH
 * @since 2020-04-23
 * @discription HelloController
 */
@RestController
public class HelloController {

    /**
     * @RestController : JSON 을 반환하는 컨트롤러로 만들어준다.
     * @return
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
