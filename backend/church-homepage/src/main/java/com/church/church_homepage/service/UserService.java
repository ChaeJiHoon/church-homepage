package com.church.church_homepage.service;

import com.church.church_homepage.domain.Role;
import com.church.church_homepage.domain.User;
import com.church.church_homepage.dto.UserLoginRequest;
import com.church.church_homepage.dto.UserSignUpRequest;
import com.church.church_homepage.jwt.JwtTokenProvider;
import com.church.church_homepage.repository.RoleRepository;
import com.church.church_homepage.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor    // final 필드의 대한 생성자를 자동으로 만들어줌
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp(UserSignUpRequest request) {
        // 1. 검증 : Username 중복 확인하기
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        // 2. 검증 : email 중복 확인
        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }

        // 3. 준비 : 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 4. 준비 : 'User' 역할 찾아오기
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("'USER' 역할이 데이터베이스에 없습니다."));

        // 5. 생성 : User 엔티티 생성
        User newUser = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
                .name(request.getName())
                .role(userRole)
                .build();

        // 6. 저장 : 데이터베이스에 User 저장
        userRepository.save(newUser);

    }

    @Transactional
    public String login(UserLoginRequest request){
        // 1. 로그인 ID/PW를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());


        // 2. 실제 검증 (사용자 비밀번호 체크)
        // authenticate 메소드가 실행될 때 CustomUserDetailsService의 loadUserByUsername 메소드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        String jwt = jwtTokenProvider.createToken(authentication);

        return jwt;
    }
}
