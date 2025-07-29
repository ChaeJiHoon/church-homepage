package com.church.church_homepage.service;

import com.church.church_homepage.domain.PrayerRequest;
import com.church.church_homepage.domain.User;
import com.church.church_homepage.dto.PrayerRequestCreateRequest;
import com.church.church_homepage.repository.PrayerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrayerRequestService {

    private final PrayerRequestRepository prayerRequestRepository;

    @Transactional
    public void createPrayerRequest(PrayerRequestCreateRequest request, User user) {

        System.out.println("---createPrayerRequest 메소드 시작 ---");
        System.out.println("Request Title: " + request.getTitle());
        System.out.println("Request Content: " + request.getContent());
        System.out.println("Request isPrivate: " + request.isPrivate());
        System.out.println("User (tempUser): " + (user != null ? user.getUsername() : "null"));

        try{
            PrayerRequest newPrayerRequest = PrayerRequest.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .user(user)
                    .isPrivate(request.isPrivate())
                    .build();

            System.out.println("PrayerRequest 엔티티 생성 완료.");

            prayerRequestRepository.save(newPrayerRequest);
            System.out.println("PrayerRequest 엔티티 저장 완료.");
        } catch (Exception e){
            System.err.println("--- createPrayerRequest 메소드에서 예외 발생 ---");
            e.printStackTrace();
            System.err.println("------------------------------------------");
            throw e;
        }
        System.out.println("--- createPrayerRequest 메소드 종료 ---");
    }

}
