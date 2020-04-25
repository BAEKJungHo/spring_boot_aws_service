package com.webeveloper.boot.aws.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author BAEKJH
 * @since 2020-04-25
 * @discription PostsRepository
 */

/**
 * JpaRepository<Entity Class, PK> 를 상속하면 기본적인 CURD 메서드가 자동으로 생성된다.
 * @Repository 추가할 필요 없음
 * 주의 !
 * - Entity 클래스와 Entity Repository 는 함께 위치해야 한다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
