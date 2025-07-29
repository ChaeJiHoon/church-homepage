package com.church.church_homepage.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCreateRequest {
    private String title;
    private String content;
    private Long categoryId;        //게시글이 속할 카테고리 ID
}
