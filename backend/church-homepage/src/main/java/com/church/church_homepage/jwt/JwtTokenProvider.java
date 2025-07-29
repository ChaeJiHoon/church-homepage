package com.church.church_homepage.jwt;


import io.jsonwebtoken.*; // JWT 관련 핵심 클래스들
import io.jsonwebtoken.security.Keys; // 키 생성 관련 유틸리티
import org.springframework.beans.factory.annotation.Value; // 설정값 주입 어노테이션
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Spring Security 인증 객체
import org.springframework.security.core.Authentication; // Spring Security 인증 인터페이스
import org.springframework.security.core.GrantedAuthority; // 권한 인터페이스
import org.springframework.security.core.authority.SimpleGrantedAuthority; // 권한 구현체
import org.springframework.security.core.userdetails.User; // Spring Security의 UserDetails 구현체
import org.springframework.stereotype.Component; // Spring Bean으로 등록

import javax.crypto.SecretKey; // 암호화 키 인터페이스
import java.nio.charset.StandardCharsets; // 문자열 인코딩
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final SecretKey key;        // JWT 서명에 사용할 비밀 키 (토큰 위변조 방지)
    private final long validityInMilliseconds; // 토큰 유효시간


    /*
     `SecretKey`: JWT는 토큰의 위변조를 막기 위해 서명을 진행
     이 서명은 서버만 아는 비밀 키(Secret Key)를 사용해서 만든다. 이 키가 외부에 노출되면 안됨!

     `@Value("${...}")`: Spring에서 application.yml이나 application.properties에 정의된 설정값을 Java 코드의 필드나 생성자 파라미터에주입받을 때 사용하는 어노테이션.
     $와 {} 안에 설정 키를 적어준다.

     */
    // 생성자: Spring이 이 클래스를 만들 때 호출
    // @Value 어노테이션을 통해 application.yml에 설정된 값을 주입
    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey, // jwt.secret-key 값을 secretKey 변수에 주입
                            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) { // jwt.token-validity-in-seconds 값을 주입

        // 주입받은 문자열 secretKey를 바이트 배열로 변환하여 SecretKey 객체로 만듬
        // Keys.hmacShaKeyFor는 HMAC SHA 알고리즘에 사용할 키를 안전하게 생성
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 초 단위의 유효 시간을 밀리초로 변환하여 저장합니다.
        this.validityInMilliseconds = tokenValidityInSeconds * 1000;
    }


    /*
    Authentication: Spring Security가 인증(로그인)에 성공하면 생성하는 객체
     -현재 로그인한 사용자의 아이디, 비밀번호, 권한 등의 정보를 담고 있다.

    Jwts.builder(): jjwt 라이브러리에서 JWT 토큰을 생성하기 위한 빌더 객체를 반환
    .setSubject(), .claim(), .signWith(),.setExpiration() 등의 메소드를 체인처럼 연결하여 토큰의 내용을 설정

    claim: JWT 토큰에 담을 정보의 한 조각, 키-값 쌍으로 이루어짐

    signWith(key, SignatureAlgorithm.HS512): 토큰의 무결성(위변조 여부)을 검증하기 위한 서명을 생성
     - key는 우리가 application.yml에서 주입받은 비밀 키이고, HS512는 서명에 사용할 해싱 알고리즘

    compact(): 빌더를 통해 설정된 모든 정보를 바탕으로 최종적인 JWT 문자열(Header.Payload.Signature 형태)을 생성
    */

    // 1. JWT 토큰 생성
    // Authentication: Spring Security의 인증 객체. 로그인 성공 후 사용자 정보(이름, 권한 등)를 담고 있다.
    public String createToken(Authentication authentication) {
        // 사용자의 권한 정보(예: "ROLE_USER", "ROLE_ADMIN")를 쉼표로 구분된 하나의 문자열로 만든다.
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)    // GrantedAuthority 객체에서 권한 문자열만 추출
                .collect(Collectors.joining(","));  // 쉼표로 연결

        long now = (new Date()).getTime();
        Date validity = new Date(now + validityInMilliseconds); // 토큰 만료 시간 계산 (현재 시간 + 유효 시간)

        // Jwts.builder()를 사용하여 JWT 토큰을 구성합니다.
        return Jwts.builder()
                .setSubject(authentication.getName())            // 토큰의 주체(Subject): 보통 사용자 ID (username)
                .claim("auth", authorities)                // 클레임(Claim): 토큰에 담을 추가 정보. 여기서는 권한 정보를 "auth"라는 이름으로 담
                .signWith(key, SignatureAlgorithm.HS512)         // 서명: 위에서 생성한 비밀 키(key)와 HS512 알고리즘으로 토큰에 서명
                .setExpiration(validity)                        // 만료 시간
                .compact();                                     // 토큰 생성
    }


    /*
    Claims: JWT 토큰의 페이로드(Payload) 부분에 담긴 정보들을 나타내는 인터페이스
    - getSubject(), get("auth") 등으로 토큰에 담긴 정보를 추출할 수 있습니다.

    Jwts.parserBuilder(): jjwt 라이브러리에서 JWT 토큰을 파싱하고 검증하기 위한 파서(Parser) 객체를 반환

    parseClaimsJws(token): 주어진 토큰 문자열을 파싱하고, 서명을 검증. 서명이 유효하지 않으면 예외를 발생

    getBody(): 파싱된 토큰에서 클레임(Payload) 부분을 가져온다.

    UsernamePasswordAuthenticationToken: Spring Security에서 사용자의 인증 정보를 나타내는 객체.
    - principal(사용자 정보),credentials(비밀번호 또는 토큰), authorities(권한)를 담는다.
    */


    // 2. JWT 토큰에서 인증 정보 조회
    // 토큰 문자열을 받아서 Spring Security의 Authentication 객체로 변환하여 반환
    public Authentication getAuthentication(String token) {

        // Jwts.parserBuilder()를 사용하여 토큰을 파싱하고 검증
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)     // 토큰 서명을 검증할 때 사용할 비밀 키 설정
                .build()
                .parseClaimsJws(token)  // 토큰을 파싱하고 서명을 검증 (JWS: JSON Web Signature)
                .getBody();   // 토큰의 페이로드(Payload) 부분, 즉 클레임(Claims)들을 가져온다.

        // 클레임에서 "auth" (권한) 정보를 가져와서 Spring Security의 GrantedAuthority 객체 리스트로 변환
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))  // 쉼표로 구분된 권한 문자열을 분리
                        .map(SimpleGrantedAuthority::new)  // 각 권한 문자열을 SimpleGrantedAuthority 객체로 변환
                        .toList();  // 리스트로 수집

        // Spring Security의 User 객체를 생성 (UserDetails 인터페이스의 구현체)
        // claims.getSubject()는 토큰 생성 시 setSubject로 넣었던 사용자 ID (username)이다.
        // 비밀번호는 이미 인증된 토큰이므로 빈 문자열("")을 넣는다.
        User principal = new User(claims.getSubject(), "", authorities);    // Spring Security의 User 객체

        // 최종적으로 UsernamePasswordAuthenticationToken 객체를 생성하여 반환
        // 이 객체는 Spring Security 컨텍스트에 저장되어 현재 사용자의 인증 정보를 나타낸다.
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 3. JWT 토큰 유효성 검증
    // 토큰 문자열을 받아서 유효하면 true, 유효하지 않으면 false를 반환
    public boolean validateToken(String token) {
        try {
            // 토큰을 파싱하고 서명을 검증합니다. 문제가 없으면 true 반환.
             Jwts.parserBuilder()
                     .setSigningKey(key)
                     .build()
                     .parseClaimsJws(token);
             return true;
         } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            // 유효하지 않은 JWT 서명
            System.out.println("잘못된 JWT 서명입니다.");
         } catch (ExpiredJwtException e){
            // 만료된 JWT 토큰
            System.out.println("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e){
            // 지원되지 않는 JWT 토큰
            System.out.println("지원되지 않는 JWT 토큰입니다");
        }  catch (IllegalArgumentException e){
            // JWT 토큰이 잘못된 경우
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false;

    }
}
