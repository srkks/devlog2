package com.devlog.service;

import com.devlog.domain.posts.Posts;
import com.devlog.domain.posts.PostsRepository;
import com.devlog.web.dto.PostsListResponseDto;
import com.devlog.web.dto.PostsResponseDto;
import com.devlog.web.dto.PostsSaveRequestDto;
import com.devlog.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    //스프링에서 bean을 주입받는 방식 중 권장하는 방식이 생성자로 주입받는 방식이다.
    //final이 선언된 모든 필드를 인자값으로 하는 @RequiredArgsConstructor에서 해결해준다.
    //이유는 해당 클래스의 의존성 관계가 바뀔 때마다 생성자 코드를 수정하는 번거로움을 해결해준다.
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)//트랜잭션 범위는 유지하되, 조회 기능만 남겨서 조회 속도가 개선된다.
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) //.map(posts -> new PostsListResponseDto(posts))  postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 Dto로 변환
                .collect(Collectors.toList()); //  -> List로 반환
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }
}
