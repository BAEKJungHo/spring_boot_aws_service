package com.webeveloper.boot.aws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author BAEKJH
 * @since 2020-04-23
 * @discription Application Main class
 */
@SpringBootApplication
public class Application {
    /**
     * 해당 클래스는 프로젝트 최상단에 위치해야한다.
     * @SpringBootApplication 이 있는 위치부터 설정을 읽어 나간다.
     * SpringApplication.run 으로 인해 내장 WAS 를 실행한다.
     * 따라서 항상 서버에 톰캣을 설치할 필요가 없고, 스프링 부트로 만들어진 실행가능한 Jar 로 실행하면 된다.
     *
     * - 스프링 부트 권장사항
     * - 내장 WAS 를 사용하는 것을 권장한다. 이유는 언제 어디서나 같은 환경에서 스프링 부트 배포가 가능하기 때문
     * - 내장 WAS 사용으로 인한 성능 이슈는 걱정 안해도됨. (배민도 문제없이 사용하고 있음)
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
