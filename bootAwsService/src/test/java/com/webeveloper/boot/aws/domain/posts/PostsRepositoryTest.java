package com.webeveloper.boot.aws.domain.posts;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @SpringBootTest
 * 통합 테스트 개념 이다.
 * H2 데이터베이스를 자동으로 실행해 준다.
 *
 * @WebMvcTest
 * web 계층에서 사용되는 테스트 어노테이션
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    /**
     * @After
     * Junit 에서 단위 테스트가 끝날 때 마다 수행되는 메서드 지정
     * 보통은 배포 전, 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해서 사용
     * 여러 테스트가 동시에 수행되면, 테스트용 DB 인 H2 에 데이터가 그대로 남아 있어서 다음 테스트 실행 시 테스트가 실패 할 수 있다.
     */
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        /**
         * save()
         * pk 가 있다면 update, 없으면 insert 쿼리 수행
         */
        postsRepository.save(
                Posts.builder()
                    .title(title)
                    .content(content)
                    .author("webeveloper")
                    .build()
        );

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build()
        );

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println("createDate = " + posts.getCreatedDate()+". modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}