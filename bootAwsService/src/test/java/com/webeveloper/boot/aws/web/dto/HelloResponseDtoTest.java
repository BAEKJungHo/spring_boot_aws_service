package com.webeveloper.boot.aws.web.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @WebMvcTest
 * web 계층에서 테스트 코드에 사용되는 어노테이션이다.
 * mockMvc 를 주입받을 수 있다.
 * @RunWith
 * 테스트를 진행할 때 JUnit 에 내장된 실행자 외에 다른 실행자를 실행시킨다.
 * 여기서는 SpringRunner 실행자를 사용
 * 즉, 스프링 부트 테스트와 JUnit 사이의 연결자 역할을 담당
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloResponseDto.class)
public class HelloResponseDtoTest {

    @Test
    public void lombok() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        /**
         * Junit assertThat vs assertj assertThat
         * asserj 의 장점
         * - CoreMatchers 와 달리 추가적으로  라이브러리가 필요하지 않다.
         * - 자동완성이 좀 더 확실하게 지원된다. (IDE 에서는 CoreMatchers 와 같은 Matcher 라이브러리의 자동완성 지원이 약하다.
         */
        // then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }

}