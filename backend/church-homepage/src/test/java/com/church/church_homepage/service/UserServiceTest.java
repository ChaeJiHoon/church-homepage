package com.church.church_homepage.service;

import com.church.church_homepage.domain.Role;
import com.church.church_homepage.domain.User;
import com.church.church_homepage.dto.UserLoginRequest;
import com.church.church_homepage.dto.UserSignUpRequest;
import com.church.church_homepage.jwt.JwtTokenProvider;
import com.church.church_homepage.repository.RoleRepository;
import com.church.church_homepage.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import static org.junit.jupiter.api.Assertions.assertEquals;



import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUpSuccessTest(){
        // given : 테스트를 위한 사전 조건 설정
        // 1. 회원가입 요청 DTO 생성
        final UserSignUpRequest request = new UserSignUpRequest("testuser", "password123", "테스트", "test@example.com");

        // 2. 'USER' 역할 객체 생성
        final Role userRole = Role.builder().name("USER").build();

        // 3. Mock 객체들의 행동 정의
        // - username 중복 검사 시, 결과가 없다고 반환하도록 설정
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        // - email 중복 검사 시, 존재하지 않는다고(false) 반환하도록 설정
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);

        // - 비밀번호 암호화 시, "encodedPassword" 라는 문자열을 반환하도록 설정
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        // - 'USER' 역할 조회 시, userRole 객체를 반환하도록 설정
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));

        // - userRepository.save 메소드가 호출될 때, 입력받은 User 객체를 그대로 반환하도록 설정
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when : 실제 테스트 대상 메소드 호출
        userService.signUp(request);

        // then : 테스트 결과 검증
        // - userRepository의 save 메소드가 User 클래스 타입의 어떤 객체로든 1번 호출되었는지 확인
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccessTest(){
        // given : 테스트를 위한 사전 조건 설정
        // 1. 로그인 DTO 생성
        final UserLoginRequest request = new UserLoginRequest("testuser", "password123");

        // 2. 가짜 Authentication 객체와 AuthenticationManager 객체 생성
        Authentication authentication = mock(Authentication.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        // 3. Mock 객체들의 행동 정의
        // - authenticationManagerBuilder.getObject()가 가짜 authenticationManager를 반환하도록 설정
        when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);

        // - authenticationManager.authenticate()가 호출되면, 위에서 만든 가짜 authentication 객체를 반환하도록 설정
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // - jwtTokenProvider.createToken()이 호출되면, "fake.jwt.token" 이라는 문자열을 반환하도록 설정
        when(jwtTokenProvider.createToken(authentication)).thenReturn("fake.jwt.token");

        // when : 실제 테스트 대상 메소드 호출
        String jwtToken = userService.login(request);

        // then : 테스트 결과 검증
        // 반환된 JWT 토큰이 우리가 예상한 "fake.jwt.token"과 일치하는지 확인
        assertEquals("fake.jwt.token", jwtToken);

        // jwtTokenProvider의 createToken 메소드가 정확히 1번 호출되었는지 확인
        verify(jwtTokenProvider, times(1)).createToken(authentication);

    }

}
