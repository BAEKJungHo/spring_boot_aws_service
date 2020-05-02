package com.webeveloper.boot.aws.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  Role {

    /**
     * 스프링 시큐리티에서는 권한 코드에 항상 ROLE_ 키워드가 앞에 있어야 한다.
     * 따라서 코드별 키값을 ROLE_GUEST, ROLE_USER 등으로 지정한다.
     */
    GUEST("ROLE_GUEST", "게스트"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}
