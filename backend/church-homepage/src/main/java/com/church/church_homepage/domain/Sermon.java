package com.church.church_homepage.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "sermons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sermon extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "preacher", nullable = false, length = 100)
    private String preacher;

    @Column(name = "sermon_date", nullable = false)
    private LocalDate sermonDate;

    @Lob
    @Column(name = "script_content", nullable = false)
    private String scriptContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Builder
    public Sermon(String title, String preacher, LocalDate sermonDate, String scriptContent, User user) {
        this.title = title;
        this.preacher = preacher;
        this.sermonDate = sermonDate;
        this.scriptContent = scriptContent;
        this.user = user;
    }
}
