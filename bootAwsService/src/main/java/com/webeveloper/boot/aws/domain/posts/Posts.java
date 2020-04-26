package com.webeveloper.boot.aws.domain.posts;

import com.webeveloper.boot.aws.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author BAEKJH
 * @since 2020-04-25
 * @discription Posts
 */

/**
 * 롬복 어노테이션과, JPA 어노테이션 등 섞어서 사용하는 경우
 * 주요 어노테이션을 클래스와 가깝게 둔다.
 * 롬복은 필수 어노테이션이 아니기 때문이다.
 * 나중에 코틀린 등 새언어로 전환할 때 롬복이 필요 없을 경우 쉽게 제거할 수 있다.
 *
 * @Entity
 * 테이블과 매핑될 클래스임을 나타낸다.
 * 기본값으로 클래스의 카멜케이스 이름을 스네이크 네이밍으로 테이블 이름을 매칭한다.
 * SalesManager.java > sales_manager table
 *
 * 엔티티 클래스는 절대 setter 메서드를 만들지 않는다.
 * 대신 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메서드를 추가해야한다.
 * @Setter 가 없는 상황에서 어떻게 값을 채워 DB에 삽입?
 * - 기본적으로는 생성자를 통해 최종값을 채운 후 DB에 삽입
 * - 값 변경이 필요한 경우 해당 이벤트에 맞는 public 메서드 호출
 * - 생성자 대신 @Builder 도 가능 (@Buiilder 추천)
 * - 생성자의 단점은 지금 채워야 할 필드가 무엇인지 명확히 지정할 수 없다.
 * - 하지만 빌더는 어떤 필드에 어떤 값을 채워야 할지 명확
 * Example.builder()
 *  .a(a)
 *  .b(b)
 */
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    /**
     * @Id
     * Pk 필드를 나타냄
     *
     * @GeneratedValue
     * PK 의 생성 규칙을 의미
     * GenerationType.IDENTITY 를 지정해야 스프링 부트 2.0 이상 부터는 auto increment 지원
     *
     * Entity 의 PK 는 가급적 Long 타입의 auto increment 를 추천
     * MySQL 기준으로 위 처럼 선언하면 bigInt 가 되는데
     * 주민등록번호와 같이 비지니스상 유니크 키나, 여러 키가 조합된 복합키를 PK 로 잡을 경우 난감한 상황이 종종 발생
     * - FK 를 맺을 때 다른 테이블에서 복합키를 전부 갖고 있거나, 중간 테이블을 하나 더 둬야  하는 상황이 발생
     * - 인덱스에 좋은 영향을 끼치지 못함
     * - 유니크한 조건이 변경될 경우 PK 전체를 수정해야하는 일이 발생
     * - 주민등록번호, 복합키 등은 유니크 키로 별도로 추가하는게 좋다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @Column
     * 테이블의 컬럼을 나타냄, 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이다.
     * 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있는 경우 사용
     * 문자열이 varchar(255) 가 기본인데, 사이즈를 500으로 늘리고 싶거나, type 을 text 로 하고싶은 경우
     */
    @Column(length = 500, nullable = false)
    private String title;

    /**
     * columnDefinition 은 컬럼의 Type 을 지정
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
