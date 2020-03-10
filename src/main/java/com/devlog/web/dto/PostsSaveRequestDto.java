package com.devlog.web.dto;

import com.devlog.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    //request,response용 dto는 view를 위한 클래스라 자주 변경이 필요하다.
    //controller에서 결과값으로 테이블을 조인해서 줘야 할 경우가 빈번하다.
    //entity클래스와 controller에서 쓸 dto는 분리해서 사용해야 한다.
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
