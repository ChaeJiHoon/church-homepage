package com.church.church_homepage.controller;


import com.church.church_homepage.domain.Category;
import com.church.church_homepage.dto.PostCreateRequest;
import com.church.church_homepage.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category savedCategory;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        // 게시글을 생성하려면 카테고리가 DB에 먼저 존재해야함
        // 각 테스트 실행 전에 카테고리를 미리 생성하고 저장
        savedCategory = categoryRepository.findByName("자유게시판")
                .orElseGet(() -> categoryRepository.save(Category.builder().name("자유게시판").build()));
    }

    @Test
    @DisplayName("게시글 생성 API 통합 테스트 - 성공")
    @WithMockUser
    void createPostSuccessTest() throws Exception{
        // given
        // 1. 게시글 생성 요청 DTO를 생성, 카테고리 ID 는 위에서 저장한 ID를 사용
        PostCreateRequest request = new PostCreateRequest("통합 테스트 제목", "통합 테스트 내용", savedCategory.getId());

        // 2. DTO 객체를 HTTP 요청 본문에 담기 위해 JSON 문자열로 변환
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        // mockMvx를 사용하여 실제 API를 호출하는 것 처럼 테스트를 수행
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
