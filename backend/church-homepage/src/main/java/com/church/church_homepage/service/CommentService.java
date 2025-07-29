package com.church.church_homepage.service;

import com.church.church_homepage.domain.Comment;
import com.church.church_homepage.domain.Post;
import com.church.church_homepage.domain.Sermon;
import com.church.church_homepage.domain.User;
import com.church.church_homepage.dto.CommentCreateRequest;
import com.church.church_homepage.repository.CommentRepository;
import com.church.church_homepage.repository.PostRepository;
import com.church.church_homepage.repository.SermonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final SermonRepository sermonRepository;

    @Transactional
    public void createComment(CommentCreateRequest request, User user){

        System.out.println("---createComment 메소드 시작 ---");
        System.out.println("Request Content: " + request.getContent());
        System.out.println("Request CommentableID(): " + request.getCommentableId());
        System.out.println("Request CommentableType(): " + request.getCommentableType());
        System.out.println("User (tempUser): " + (user != null ? user.getUsername() : "null"));

        try{
            Post post = null;
            Sermon sermon = null;

            if("POST".equalsIgnoreCase(request.getCommentableType())){
                post = postRepository.findById(request.getCommentableId())
                        .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시글입니다."));
            }else if("SERMON".equalsIgnoreCase(request.getCommentableType())) {
                sermon = sermonRepository.findById(request.getCommentableId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 설교입니다."));
            }else{
                throw new IllegalArgumentException("지원하지 않는 댓글 타입입니다 : " + request.getCommentableType());
            }

            System.out.println("Parent Entity Found: " + (post != null ? "Post ID: " + post.getId() : "Sermon ID: " +
                    sermon.getId()));

            Comment newComment = Comment.builder()
                    .content(request.getContent())
                    .user(user)
                    .post(post)
                    .sermon(sermon)
                    .build();

            System.out.println("Comment 엔티티 생성 완료.");


            commentRepository.save(newComment);
            System.out.println("Comment 엔티티 저장 완료.");
        }catch (Exception e){
            System.err.println("--- createComment 메소드에서 예외 발생 ---");
            e.printStackTrace();
            System.err.println("------------------------------------------");
            throw e;
        }

        System.out.println("--- createComment 메소드 종료 ---");
    }
}
