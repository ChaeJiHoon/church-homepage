package com.church.church_homepage.config;

import com.church.church_homepage.domain.Category;
import com.church.church_homepage.domain.Role;
import com.church.church_homepage.repository.CategoryRepository;
import com.church.church_homepage.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception{

        // USER 역할이 없으면 추가
        if(roleRepository.findByName("USER").isEmpty()) {
            roleRepository.save(new Role(null, "USER"));
        }

        // ADMIN 역할이 없으면 추가
        if(roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(new Role(null, "ADMIN"));
        }

        // 공지사항 역할이 없으면 추가
        if(categoryRepository.findByName("공지사항").isEmpty()) {
            categoryRepository.save(new Category(null, "공지사항"));
        }

        // 자유게시판 역할이 없으면 추가
        if(categoryRepository.findByName("자유게시판").isEmpty()) {
            categoryRepository.save(new Category(null, "자유게시판"));
        }

        // 교회소식 역할이 없으면 추가
        if(categoryRepository.findByName("교회소식").isEmpty()) {
            categoryRepository.save(new Category(null, "교회소식"));
        }

    }
}
