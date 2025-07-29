package com.church.church_homepage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        System.out.println("TestController: hello() called!"); // 콘솔에 출력되는지 확인
        return "Hello from TestController!";
    }
}
