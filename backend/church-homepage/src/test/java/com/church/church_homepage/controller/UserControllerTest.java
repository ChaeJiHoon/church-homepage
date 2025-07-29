package com.church.church_homepage.controller;

import com.church.church_homepage.dto.UserSignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원가입 API 통합 테스트 - 성공")
    void userSingUpSuccessTest() throws Exception{
        // given
        // 1. UserSignUpRequest DTO 객체를 생성
        UserSignUpRequest request = new UserSignUpRequest("newUser", "1234", "새로운유저", "new.user@exam.com");

        // 2. DTO 객체를 HTTP 요청 본문에 담기 위해 JSON 문자열로 변환
        String jsonContent = objectMapper.writeValueAsString(request);

        // when & then
        // mockMvx를 사용하여 실제 API를 호출하는 것 처럼 테스트를 수행
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
