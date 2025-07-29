package com.church.church_homepage.controller;


import com.church.church_homepage.dto.UserLoginRequest;
import com.church.church_homepage.dto.UserLoginResponse;
import com.church.church_homepage.dto.UserSignUpRequest;
import com.church.church_homepage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")         // POST /api/users/signup 요청을 처리
    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest request){  // RequestBody : HTTP 요청의 본문(Body)에 담겨온 데이터를 Java 객체로 자동 변환해주는 역할
        try {
            userService.signUp(request);
            return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);    // 400 Bad Request
        } catch (Exception e){
            return new ResponseEntity<>("서버 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal server error
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        try{
            String jwt = userService.login(request);
            return new ResponseEntity<>(new UserLoginResponse(jwt), HttpStatus.OK); // 200 OK
        }catch (Exception e) {
            return new ResponseEntity<>(new UserLoginResponse(null), HttpStatus.UNAUTHORIZED);
        }
    }
}
