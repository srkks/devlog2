package com.devlog.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // mybatis에서 dao라고 불리는 db layer 접근자
    // 인터페이스로 생성 후 JpaRepository<entiry, pk>를 상속하면 기본적인 crud 메소드가 생성된다. @Repository 불필요

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
