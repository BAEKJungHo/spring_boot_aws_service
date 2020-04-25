# 스프링 부트와 AWS 로 혼자 구현하는 웹 서비스

- 지은이 : 이동욱
- 출판사 : 프리렉

## 자바와 스프링을 사용하는 대기업

- 네이버
- 카카오
- 라인
- 쿠팡
- 배달의민족

## 스프링 부트를 사용하는 이유

스프링을 사용하는 경우 수많은 설정을 해야하는데, 스프링 부트는 그러한 많은 설정들이 자동화되어 비지니스 로직에 집중할 수 있다.
그리고 서버에 톰캣과 같은 웹 애플리케이션 서버를 설치할 필요도 없고 jar 만 있으면 서비스를 운영할 수 있다.

## 개발환경

- JDK 1.8
- Gradle 4.8 ~ 4.10.2

## Gradle Test 코드 동작하게 설정하기

![Gradle Test](images/gradle.JPG)

## Annotation Processing 

![Annotation Processing](images/annotationprocessing.JPG)

## JPA vs SQL Mapper

iBatis, MyBatis 는 SQL Mapper 이다. SQL Mapper 는 어떻게 데이터를 저장할지에 초점이 맞춰진 기술이다.

반면, JPA 는 SQL Mapper 를 사용하는 경우 실제로 개발하는 시간보다 SQL 을 다루는 시간이 더 많아지는 것을 해결하기위해 나타난 해결책이며, 객체지향 프로그래밍을 좀 더 효과적으로 지원한다.

> JPA(자바 표준 ORM, Object Relation Mapping) 는 객체를 매핑하는것이고, SQL Mapper 는 쿼리를 매핑하는 것이다.

- 배민, 쿠팡, NHN 등 자사 서비스 개발하는 곳은 `Spring Boot` 와 `JPA` 를 표준으로 사용
- 아직 SI 환경은 `Spring & MyBatis` 또는 `Spring Boot & MyBatis` 사용

- 객체지향 프로그래밍 예제

```java
// 부모가 되는 객체 가져오기
// 누가봐도 User 와 Group 은 부모-자식 관계임을 알 수 있다.
User user = findUser();
Group group = user.getGroup();
```

- SQL Mapper 사용시 문제점(부모-자식 관계를 알 수 없다.)

```java
// 각각 따로 조회
// 상속, 1:N 다양한 객체 모델링을 데이터베이스로 구현할 수 없음
User user = userRepository.findUser();
Group group = groupRepository.findGroup(user.getGroupId));
```

이러한 문제점(데이터 베이스 모델링에만 집중하게 되는 현상)을 해결하기 위해 JPA 가 등장. 서로 지향하는 바가 다른 2개 영역(객체지향 프로그래밍 언어와
관계형 데이터페이스)을 `중간에서 패러다임 일치` 시켜주기 위한 기술.

즉, 개발자는 객체지향 프로그래밍을 하고 JPA 가 이를 관계형 데이터베이스에 맞게 SQL 을 대신 생성해서 실행.

따라서, SQL 에 종속적인 개발을 하지 않아도 된다.

## Spring Data JPA

Spring Data JPA -> Hibernate -> JPA

Hibernate 를 쓰는것과 Spring Data JPA 를 쓰는 것 사이에는 큰 차이가 없지만, 스프링 진영에서는 `Spring Data JPA` 를 쓰는 것을 권장하고 있다.

한단 계 더 감싸놓은 Spring Data JPA 가 등장한 이유는 아래와 같다.

- `구현체 교체의 용이성`
  - Hibernate 외에 다른 구현체로 쉽게 교체하기 위함
- `저장소 교체의 용이성`
  - 관계형 데이터베이스 외에 다른 저장소로 쉽게 교체하기 위함
  - 관계형 DB 를 사용하다가 트래픽이 많아져서 관계형 DB 로 감당이 안되는 경우, 이때 MongoDB 로 교체가 필요한 경우 개발자는 Spring Data JPA 에서 Spring Data MongoDB 로 의존성 교체만 하면 된다.
  
> JPA 의 가장 큰 단점은, 높은 러닝 커브, 하지만 JPA 를 사용함으로써 얻는 이점은 크다. CRUD 쿼리를 직접 작성할 필요가 없다. 또한 부모-자식 관계 표현, 1:N 관계 표현, 상태와 행위를 한 곳에 관리하는 등 객체지향 프로그래밍을 쉽게할 수 있다. JPA 는 여러 성능 이슈 해결책들이 대비된 상태라, 이를 잘 활용하면 네이티브 쿼리 만큼 퍼포먼스가 난다.

## Spring 웹 계층

![Spring 웹 계층](images/layer.JPG)

스프링 MVC 패턴을 사용할 때, 비지니스 로직을 service 계층에서 처리를 했는데, JPA 를 사용하게 되는경우 비지니스 로직 처리를 `도메인` 에서 해야한다.
사실 Service 는 `트랜잭션, 도메인간 순서보장` 역할만 담당한다.

이때 비지니스 처리 로직이 도메인으로 이동하는 이유는, JPA 를 사용하기 전 @Repository 가 붙은 클래스 역할이 @Service 가 붙은 클래스로 이동하기 때문이다. 그럼 기존 Repository 는 JPA 에서 어떻게 변하냐면 아래처럼 `JpaRepository<Entity Class, PK>` 를 상속받아 사용하게끔 변한다.

주의할 점은 Entity Class 와 Entity Repository 는 같은 위치에 존재해야한다.

- domain
  - 모듈명
    - Entity class
    - Entity Repository
 
 혹은
 
 - 모듈명
  - domian
    - Entity class
    - Entity Repository

```java
/**
 * JpaRepository<Entity Class, PK> 를 상속하면 기본적인 CURD 메서드가 자동으로 생성된다.
 * @Repository 추가할 필요 없음
 * 주의 !
 * - Entity 클래스와 Entity Repository 는 함께 위치해야 한다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
```

- Web Layer
  - 컨트롤러, 필터, 인터셉터, JSP/Freemarker 등 뷰 템플릿 영역
  - 이외에도 외부 요청과 응답에 대한 전반적인 영역을 담당
- Service Layer
  - @Service 에 사용되는 영역
  - @Transactional 이 사용되어야 하는 영역이기도함
- Repository Layer
  - 데이터베이스와 같이 데이터 저장소에 접근하는 영역
  - DAO 영역
- Dtos
  - DTO(Data Transfer Object) `계층 간에 데이터 교환을 위한 객체` 를 이야기한다.
  - 예를 들어 뷰 템플릿 엔진에서 사용될 객체나 Repository Layer 에서 결과로 넘겨준 객체 등이 이들을 의미
- Domain Model
  - 도메인이라 불리는 개발 대상을 모든 사람이 동일한 관점에서 이해할 수 있고 공유할 수 있도록 단순화시킨 것을 도메인 모델이라 한다.
  - @Entity 가 사용되는 영역
  - 무조건, 데이터베이스 테이블과 관계가 있어야만 하는 것은 아니다.
  - VO 처럼 값 객체들도 이 영역에 해당
 
