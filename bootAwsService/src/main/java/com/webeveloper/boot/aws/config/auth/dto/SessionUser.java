package com.webeveloper.boot.aws.config.auth.dto;

import com.webeveloper.boot.aws.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * SessionUser 에는 인증된 사용자 정보만 필요하다.
 * 따라서 필요한 정보들만 선언
 */
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
