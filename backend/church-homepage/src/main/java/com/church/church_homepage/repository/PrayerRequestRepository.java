package com.church.church_homepage.repository;

import com.church.church_homepage.domain.PrayerRequest;
import com.church.church_homepage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrayerRequestRepository extends JpaRepository<PrayerRequest,Long>{

    List<PrayerRequest> findByTitleContaining(String keyword);

    List<PrayerRequest> findByUser(User user);

    List<PrayerRequest> findByIsPrivateTrue();
    List<PrayerRequest> findByIsPrivateFalse();

}
