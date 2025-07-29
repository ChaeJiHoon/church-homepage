package com.church.church_homepage.service;


import com.church.church_homepage.domain.PrayerRequest;
import com.church.church_homepage.domain.User;
import com.church.church_homepage.dto.PrayerRequestCreateRequest;
import com.church.church_homepage.repository.PrayerRequestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PrayerRequestServiceTest {

    @Mock
    private PrayerRequestRepository prayerRequestRepository;

    @InjectMocks
    private PrayerRequestService prayerRequestService;

    @Test
    @DisplayName("기도 요청 서비스 생성성공 테스트")
    void createPrayerRequestSuccessTest(){
        // given
        final PrayerRequestCreateRequest request = new PrayerRequestCreateRequest("testTitle", "testContent", false);

        final User user = User.builder().
                username("testuser").
                password("1234").
                email("test@exam.com").
                build();

        when(prayerRequestRepository.save(any(PrayerRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        prayerRequestService.createPrayerRequest(request, user);

        verify(prayerRequestRepository).save(any(PrayerRequest.class));
    }
}
