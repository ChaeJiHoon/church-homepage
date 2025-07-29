package com.church.church_homepage.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    // fetch = FetchType.EAGAR-> 즉시 로딩
    // 묻지도 따지지도 말고 다 가져와!
    // 동작방식 : Post 엔티티를 조회하면 그 시점에 바로 User, Category 엔티티까지 함께 조회해서 채움
    // SELECT 쿼리가 즉시 실행됨

    // fetch = FetchType.LAZY-> 지연 로딩
    // 일단 나부터 갖고 오고, 필요하면 그때 불러줘!
    // 동작방식 : Post 엔티티를 조회할때 일단 'Post' 정보만 가져오고, 연관된 User, Category 필드에는 진짜객체 대신 가짜 객체를 채워 놓는다
    // 그러고 나서 실제로 User를 사용하는 시점에 비로소 User을 조회하는 SELECT 쿼리 실행
    // 성능 최적화: 당장 필요 없는 데이터는 조회하지 않으므로, 불필요한 쿼리가 발생하지 않음
    // 모든 연관관계는 `FetchType.LAZY` (지연 로딩)으로 설정하는 것을 기본 원칙으로 생각하기
    // `@ManyToOne`, `@OneToOne` 어노테이션은 기본 Fetch 전략이 `EAGER` 이므로, 반드시 `fetch = FetchType.LAZY`를 명시적으로 적어줘야 한다고한다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 필드 추가 하기,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sermon_id")
    private Sermon sermon;

    @Builder
    public Comment(String content, User user, Post post, Sermon sermon) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.sermon = sermon;
    }
}
