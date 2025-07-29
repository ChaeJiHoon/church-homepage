package com.church.church_homepage.service;

import com.church.church_homepage.domain.Sermon;
import com.church.church_homepage.domain.User;
import com.church.church_homepage.dto.SermonCreateRequest;
import com.church.church_homepage.repository.SermonRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SermonService {

    private final SermonRepository sermonRepository;

    @Transactional
    public void createSermon(SermonCreateRequest request, User user) {

        Sermon newSermon = Sermon.builder()
                .title(request.getTitle())
                .preacher(request.getPreacher())
                .sermonDate(request.getSermonDate())
                .scriptContent(request.getScriptContent())
                .user(user)
                .build();

        sermonRepository.save(newSermon);
    }

}
