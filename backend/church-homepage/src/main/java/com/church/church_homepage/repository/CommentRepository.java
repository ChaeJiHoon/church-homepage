package com.church.church_homepage.repository;

import com.church.church_homepage.domain.Comment;
import com.church.church_homepage.domain.Post;
import com.church.church_homepage.domain.Sermon;
import com.church.church_homepage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUser(User user);

    List<Comment> findByPost(Post post);

    List<Comment> findBySermon(Sermon sermon);

    // 특정 게시글의 댓글을 '생성 시간(createdAt) 오름차순(Asc)'으로 정렬해서 조회
    List<Comment> findByPostOrderByCreatedAtAsc(Post post);

    // 특정 설교의 댓글을 '생성 시간(createdAt) 오름차순(Asc)'으로 정렬해서 조회
    List<Comment> findBySermonOrderByCreatedAtAsc(Sermon sermon);

}
