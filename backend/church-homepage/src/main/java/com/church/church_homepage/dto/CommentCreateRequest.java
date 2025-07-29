package com.church.church_homepage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentCreateRequest {
    private String content;
    private Long commentableId;         //  댓글이 달릴 부모 엔티티의 ID (Post Id 또는 Sermon Id)
    private String commentableType;     //  댓글이 달릴 부모 엔티티의 타입 (POST, SERMON)
}
