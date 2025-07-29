package com.church.church_homepage.service;


import com.church.church_homepage.domain.Category;
import com.church.church_homepage.domain.Post;
import com.church.church_homepage.domain.User;
import com.church.church_homepage.dto.PostCreateRequest;
import com.church.church_homepage.repository.CategoryRepository;
import com.church.church_homepage.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createPost(PostCreateRequest request, User user) {

        // 카테고리 id를 DB에서 찾아옴
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        // 모든 재료를 조합해서 새로은 Post 엔티티 생성
        Post newPost = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .category(category)
                .build();

        // 완성된 Post 엔티티를 DB에 저장
        postRepository.save(newPost);
    }

}
