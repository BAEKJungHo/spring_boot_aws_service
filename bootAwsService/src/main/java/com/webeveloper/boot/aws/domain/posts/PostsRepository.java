package com.webeveloper.boot.aws.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

    /**
     * 규모가 있는 프로젝트에서의 데이터 조회는 FK 의 조인, 복잡한 조건 등으로 인해
     * Entity 클래스 만으로 처리하기가 어렵다.
     * 따라서 조회용 프레임 워크를 사용한다.(QueryDsl, JOOQ, MyBatis)
     *
     * QueryDsl 추천
     * - 타입 안정성 보장 (JOOQ 도 동일, 메서드 기반으로 쿼리 작성)
     * - 국내 많은 회사에서 사용 중 (쿠팡, 배민 등)
     * - 래퍼런스가 많다.
     */

    /**
     * Spring Data JPA 에서 제공하지 않는 메서드는 아래 처럼 쿼리로 작성 가능하다.
     * @return
     */
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

}
