package com.church.church_homepage.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter     // 컨트롤러에서 요청 데이터를 바인딩 하기위해 필요
@AllArgsConstructor
public class UserSignUpRequest {
    private String username;
    private String password;
    private String name;
    private String email;
}
