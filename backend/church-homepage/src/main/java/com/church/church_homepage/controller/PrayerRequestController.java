package com.church.church_homepage.controller;


import com.church.church_homepage.domain.User;
import com.church.church_homepage.dto.PrayerRequestCreateRequest;
import com.church.church_homepage.dto.SermonCreateRequest;
import com.church.church_homepage.service.PrayerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prayerRequest")
@RequiredArgsConstructor
public class PrayerRequestController {

    private final PrayerRequestService prayerRequestService;

    @PostMapping
    public ResponseEntity<String> createPrayerRequest(@RequestBody PrayerRequestCreateRequest request){
        try {
            // 임시 User 객체
            User tempUser = null; // 실제 로그인한 사용자 정보가 들어갈 자리
            prayerRequestService.createPrayerRequest(request, tempUser);
            return new ResponseEntity<>("기도요청이 성공적으로 생성되었습니다." , HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);    // 400 Bad Request
        }catch (Exception e){
            return new ResponseEntity<>("서버 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal server error
        }
    }
}
