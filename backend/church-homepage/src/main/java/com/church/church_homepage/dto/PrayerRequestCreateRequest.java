package com.church.church_homepage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PrayerRequestCreateRequest {

    private String title;
    private String content;
    private boolean isPrivate;
}
