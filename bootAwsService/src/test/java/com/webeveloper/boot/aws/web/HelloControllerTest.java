package com.webeveloper.boot.aws.web;

import com.webeveloper.boot.aws.domain.posts.Posts;
import com.webeveloper.boot.aws.domain.posts.PostsRepository;
import com.webeveloper.boot.aws.web.dto.PostsUpdateRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        String hello = "hello";

        mockMvc.perform(get("/hello")) // hello 주소로 HTTP GET 요청
                    .andExpect(status().isOk()) // mockMvc.perform 결과의 상태 코드가 200인지
                    .andExpect(content().string(hello)); // 응답 본문의 내용이 hello 인지
    }

    @Test
    public void helloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        /**
         * jsonPath
         * - json 응답값을 필드별로 검증할 수 있는 메서드
         * - 여기서 return 값은 ResultMatcher
         * is
         * - import static org.hamcrest.Matchers.is;
         */
        mockMvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}
