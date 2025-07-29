package com.church.church_homepage.service;

import com.church.church_homepage.domain.*;
import com.church.church_homepage.dto.CommentCreateRequest;
import com.church.church_homepage.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private SermonRepository sermonRepository;

    @Mock
    private PrayerRequestRepository prayerRequestRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("게시글에 댓글 생성 성공 테스트")
    void createCommentSuccessTest(){
        // given
        final CommentCreateRequest request = new CommentCreateRequest("게시글 댓글 입니다.", 1L, "POST");

        // 2. 댓글을 작성할 사용자 객체 생성
        final User user = User.builder().build();
        ReflectionTestUtils.setField(user, "id", 99L);

        //3. 댓글이 달릴 대상 게시글 객체 생성
        final Post targetPost = Post.builder().title("테스트 게시글").content("내용").build();
        ReflectionTestUtils.setField(targetPost, "id", 1L); // 요청 DTO랑 일치 시킴

        // 4. Repository 행동 정의 : 게시물을 찾아오는 시나리오가 핵심
        // - postRepository.findById(1L)가 호출되면, 위에서 만든 targetPost를 Optional에 담아 반환하도록 설정
        when(postRepository.findById(request.getCommentableId())).thenReturn(Optional.of(targetPost));

        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        commentService.createComment(request, user);

        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(postRepository,times(1)).findById(request.getCommentableId());
        verify(sermonRepository,never()).findById(any());
        verify(prayerRequestRepository,never()).findById(any());

    }


}
