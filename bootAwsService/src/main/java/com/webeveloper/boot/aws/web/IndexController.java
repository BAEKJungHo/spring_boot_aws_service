package com.webeveloper.boot.aws.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * 머스테치를 사용하면 머스테치 스타터 덕분에 앞의 경로와 뒤의 파일 확장자가 자동으로 붙는다.
     * src/main/resources/templates + index + .mustache
     * ViewResolver 가 알아서 처리
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

}
