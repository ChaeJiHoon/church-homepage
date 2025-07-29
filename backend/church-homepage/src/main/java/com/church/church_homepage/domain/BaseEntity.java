package com.church.church_homepage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass  // 이 클래스는 테이블과 매핑되지 않고, 자식 엔티티에게 필드만 물려주는 역할
@EntityListeners(AuditingEntityListener.class)  // 엔티티의 변경을 감지하여 자동으로 값을 넣어줌
public abstract class BaseEntity {

    @CreatedDate  // 엔티티가 생성될 때의 시간이 자동 저장
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate  // 엔티티가 수정될 때의 시간이 자동 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
