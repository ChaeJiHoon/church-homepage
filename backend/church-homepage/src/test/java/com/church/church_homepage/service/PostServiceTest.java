package com.church.church_homepage.service;

import com.church.church_homepage.domain.Category;
import com.church.church_homepage.domain.Post;
import com.church.church_homepage.domain.Role;
import com.church.church_homepage.domain.User;
import com.church.church_homepage.dto.PostCreateRequest;
import com.church.church_homepage.repository.CategoryRepository;
import com.church.church_homepage.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("게시글 저장 성공 테스트")
    void savePostSuccessTest() {
        // given
        final PostCreateRequest request = new PostCreateRequest("테스트 제목", "테스트 내용", 1L);
        final String username = "testuser";
        final Role userRole = Role.builder().name("USER").build();
        ReflectionTestUtils.setField(userRole, "id", 1L);
        final User user = User.builder()
                .username(username)
                .password("1234")
                .email("cjh@naver.com")
                .role(userRole)
                .build();
        ReflectionTestUtils.setField(user, "id", 1L);
        final Category category = Category.builder().id(1L).name("자유게시판").build();

        when(categoryRepository.findById(request.getCategoryId())).thenReturn(Optional.of(category));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        postService.createPost(request, user);

        verify(postRepository).save(any(Post.class));
    }
}
