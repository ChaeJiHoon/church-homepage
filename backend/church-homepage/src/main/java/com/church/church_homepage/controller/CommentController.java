package com.church.church_homepage.controller;


import com.church.church_homepage.domain.User;
import com.church.church_homepage.dto.CommentCreateRequest;
import com.church.church_homepage.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentCreateRequest request){
        try {
            // 임시 User 객체
            User tempUser = null;
            commentService.createComment(request, tempUser);
            return new ResponseEntity<>("댓글이 성공적으로 생성되었습니다." , HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);    // 400 Bad Request
        }catch (Exception e){
            return new ResponseEntity<>("서버 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal server error
        }
    }
}
