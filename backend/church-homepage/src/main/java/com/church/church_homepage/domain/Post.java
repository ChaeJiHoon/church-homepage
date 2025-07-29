package com.church.church_homepage.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
BaseEntity 상속을 통해 앞으로 Post 엔티티를 저장하거나 수정할 때마다 JPA가 자동으로
`createdAt`과 `updatedAt` 값을 채워준다. 우리는더 이상 이 필드들에 신경 쓸 필요가 전혀 없음!
Sermon, Comment 등 다른 엔티티들도 BaseEntity를 상속받기만 하면 이 기능을 똑같이 누릴 수 있게된다!
이것이 바로 객체지향...?
 */

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)  // Post(Many)가 User(One)를 참조
    @JoinColumn(name = "user_id")       // DB의 user_id 컬럼과 매핑
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  // Post(Many)가 Category(One)를 참조
    @JoinColumn(name = "category_id")
    private Category category;


    @Builder
    public Post(String title, String content, User user, Category category) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.category = category;
    }

}