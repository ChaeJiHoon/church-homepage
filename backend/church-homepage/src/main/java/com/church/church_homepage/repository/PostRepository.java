package com.church.church_homepage.repository;

import com.church.church_homepage.domain.Category;
import com.church.church_homepage.domain.Post;
import com.church.church_homepage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 결과가 여러 건일 수 있는 조회의 반환 타입은 `List<Post>` 를 사용해야 한다.
    // 특정 카테고리에 속한 모든 게시글 "목록"을 조회
    List<Post> findByCategory(Category category);

    // 특정 사용자가 작성한 모든 게시글 "목록"을 조회
    List<Post> findByUser(User user);

    // 특정 키워드가 제목에 포함된 모든 게시글 "목록"을 검색
    List<Post> findByTitleContaining(String keyword);
}
