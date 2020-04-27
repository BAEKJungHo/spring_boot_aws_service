package com.webeveloper.boot.aws.service.posts;

import com.webeveloper.boot.aws.domain.posts.Posts;
import com.webeveloper.boot.aws.domain.posts.PostsRepository;
import com.webeveloper.boot.aws.web.dto.PostsListResponseDto;
import com.webeveloper.boot.aws.web.dto.PostsResponseDto;
import com.webeveloper.boot.aws.web.dto.PostsSaveRequestDto;
import com.webeveloper.boot.aws.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BAEKJH
 * @since 2020-04-25
 * @discription PostsService
 */

/**
 * DTO 는 View Layer 에 가까운 클래스이다.
 * 절대 Entity 용으로 사용하면 안된다.
 * 즉, Entity 클래스를 Response/Request 용으로 사용하면 안된다.
 */
@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    /**
     * 수정 로직
     * - 키값 받아서, 해당 키값 게시글 데이터 조회
     * - 수정하고 싶은 데이터들만 Entity 클래스의 update 메서드 호출해서 값 변경
     * @param id
     * @param requestDto
     * @return
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        /**
         * 핵심
         * update 는 쿼리 날리는게 아니라 값만 저장하는데 return 에서 Posts 객체를 던지는게 아니라 키값을 던진다?
         * 가능한 이유는 "JPA 의 영속성 컨텍스트" 때문이다.
         *
         * 영속성 컨텍스트
         * - 엔티티를 영구 저장하는 환경
         * - JPA 의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함 되어있냐 아니냐로 갈린다.
         *
         * 더티 체킹(dirty checking)(https://jojoldu.tistory.com/415)
         * JPA의 엔티티 매니저(entity manager) 가 활성화된 상태로 Spring Data JPA 를 쓴다면 (기본옵션)
         * 트랜잭션 안에서 데이터베이스에서 데이터를 가져오면, 이 데이터는 영속성 컨텍스트가 유지된 상태이다.
         * 이 상태에서 해당 데이터 값을 변경하면 "트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영한다"
         * 즉, 영속성 컨텍스트가 유지된 상태에서 엔티티 객체의 값만 변경하면 별도로 update 쿼리를 날릴 필요가 없다.
         * 이를 더티 체킹 이라 한다.
         *
         * Dirty : 상태의 변화가 생김
         * Dirty Checking : 상태 변경 검사
         *
         * JPA에서는 엔티티를 조회하면 해당 엔티티의 조회 상태 그대로 스냅샷을 만들어놓는다.
         * 그리고 트랜잭션이 끝나는 시점에는 이 스냅샷과 비교해서 다른점이 있다면 Update Query를 데이터베이스로 전달한다.
         *
         * Dirty Checking으로 생성되는 update 쿼리는 기본적으로 모든 필드를 업데이트한다.
         * JPA에서는 전체 필드를 업데이트하는 방식을 기본값으로 사용한다.
         *
         * 따라서 부분만 업데이트 하고 싶은 경우 Entity class 에 update 메서드를 만들어 사용한다.
         */
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // .map(posts -> new PostListResponseDto(posts)), PostListResponseDto 로 변환
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }


}
