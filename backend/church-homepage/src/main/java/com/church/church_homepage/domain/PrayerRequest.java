package com.church.church_homepage.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prayer_requests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrayerRequest extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    // 익명 작성을 위해 nullable = false 제거
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    @Builder
    public PrayerRequest(String title, String content, User user, boolean isPrivate) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.isPrivate = isPrivate;
    }
}
