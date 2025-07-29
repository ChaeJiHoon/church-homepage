package com.church.church_homepage.repository;

import com.church.church_homepage.domain.Sermon;
import com.church.church_homepage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SermonRepository extends JpaRepository<Sermon, Long> {

    List<Sermon> findByTitleContaining(String keyword);

    List<Sermon> findByPreacher(String preacher);

    List<Sermon> findByUser(User user);

    List<Sermon> findBySermonDate(LocalDate date);

    // 특정 기간 사이의 설교 검색
    List<Sermon> findBySermonDateBetween(LocalDate startDate, LocalDate endDate);

    // 최신 설교 순 정렬롤 가져오기
    List<Sermon> findAllByOrderBySermonDateDesc();
}
