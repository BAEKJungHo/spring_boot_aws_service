package com.webeveloper.boot.aws.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * JPA Auditing 으로 생성시간/수정시간 자동화하기
 *
 * 등록일 / 수정일 문제를 자동화 시켜준다. (따로 코드를 작성할 필요 없음)
 *
 * @EnableJpaAuditing
 * Auditing 어노테이션들을 모두 활성화 시키기 위해서 Application.class 에 활성화 어노테이션 추가해야함
 *
 * BaseTimeEntity 클래스는 모든 Entity의 상위 클래스가 되어 Entity 들의
 * createdDate, modifiedDate 를 자동으로 관리해준다.
 *
 * LocalDate 와 LocalDateTime 이 DB 에 제대로 매핑되지 않는 문제가 Hibernate 5.2.10 버전에서 해결되었다.
 * 스프링 부트 2.x 버전은 자동으로 사용
 * 스프링 부트 1.x 버전은 따로 설정해줘야함
 *
 * @MappedSuperclass
 * JPA Entity 클래스들이 BaseTimeEntity 를 상속할 경우, BaseTimeEntity 의 필드들을 컬럼으로 인식하게 해준다.
 *
 * @EntityListeners(AuditingEntityListener.class)
 * BaseEntityTime 클래스에 Auditing 기능을 포함시킨다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    /**
     * @CreatedDate
     * 엔티티가 생성되어 저장될 때 시간이 자동 저장된다.
     */
    @CreatedDate
    private LocalDateTime createdDate;

    /**
     * 조회한 Entity 의 값을 변경할 때 시간이 자동 저장된다.
     */
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
