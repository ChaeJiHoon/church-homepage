package com.church.church_homepage.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SermonCreateRequest {

    private String title;
    private String preacher;
    private LocalDate sermonDate;
    private String scriptContent;
}
