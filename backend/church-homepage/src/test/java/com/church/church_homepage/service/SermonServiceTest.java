package com.church.church_homepage.service;


import com.church.church_homepage.domain.Sermon;
import com.church.church_homepage.domain.User;
import com.church.church_homepage.dto.SermonCreateRequest;
import com.church.church_homepage.repository.SermonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SermonServiceTest {

    @Mock
    private SermonRepository sermonRepository;

    @InjectMocks
    private SermonService sermonService;

    @Test
    @DisplayName("설교 생성 성공 테스트")
    void createSermonSuccessTest(){
        // given
        final LocalDate sermonDate = LocalDate.of(2021, 1, 1);
        final SermonCreateRequest request = new SermonCreateRequest("테스트 설교 제목", "테스트 설교자", sermonDate,"요한복음 1장 1절 말씀");

        // 테스트용 User 객체 생성
        final User user = User.builder().
                username("testuser").
                password("test1234").
                email("test@exam.com").
                build();

        when(sermonRepository.save(any(Sermon.class))).thenAnswer(invocation -> invocation.getArgument(0));

        sermonService.createSermon(request, user);

        verify(sermonRepository).save(any(Sermon.class));

    }
}
