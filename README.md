# ⛪ 교회 홈페이지 개발 프로젝트

 **Spring Boot와 React를 사용하여 구축한 모던 웹 기반 교회 홈페이지 프로젝트입니다.**


---
## 🌟 주요 기능

-   **사용자 관리:** JWT 토큰 기반의 안전한 회원가입 및 로그인/인증/인가 기능
-   **게시판:** 다양한 주제의 게시글 작성, 조회, 수정, 삭제 (CRUD) 기능
-   **설교 말씀:** 주일 설교 영상 및 내용 아카이빙 기능
-   **기도제목:** 성도들이 함께 기도할 수 있는 기도제목 공유 기능
-   **댓글:** 각 게시물, 설교, 기도제목에 대한 소통을 위한 댓글 기능

---

## 🛠️ 기술 스택

### Backend

-   **Framework:** Spring Boot 3.5.3
-   **Language:** Java 17
-   **Database:** H2 (테스트), MySQL
-   **ORM:** Spring Data JPA
-   **Security:** Spring Security, JWT
-   **Testing:** JUnit 5, Mockito
-   **API-Docs:** Swagger (Springdoc)

### Frontend

-   **Framework:** React
-   **Language:** TypeScript

---
## 🌳 프로젝트 구조

```

  ├── 📄 README.md
  ├── 📂 backend
  │   └── 📂 church-homepage
  │       ├── 📄 build.gradle
  │       └── 📂 src
  │           └── 📂 main
  │               ├── 📂 java
  │               │   └── 📂 com/church/church_homepage
  │               │       ├── 📂 config      # (Spring Security, Swagger 등 설정)
  │               │       ├── 📂 controller # (API 엔드포인트)
  │               │       ├── 📂 domain      # (JPA 엔티티)
  │               │       ├── 📂 dto         # (데이터 전송 객체)
  │               │       ├── 📂 jwt         # (JWT 관련 로직)
  │               │       ├── 📂 repository  # (Spring Data JPA 리포지토리)
  │               │       └── 📂 service     # (비즈니스 로직)
  │               └── 📂 resources
  │                   └── 📄 application.yml
  └── 📂 frontend
      ├── 📄 package.json
      └── 📂 src
          ├── 📄 App.tsx
          └── 📄 index.tsx

```
---

## 📖 API 문서 확인하기

 **Swagger UI**를 통해 확인하고 직접 테스트해볼 수 있습니다.

1.  백엔드 애플리케이션을 실행
2.  웹 브라우저에서 아래 주소로 접속
-   **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

### 🔑 API 테스트를 위한 인증 방법

인증이 필요한 API(게시글 작성 등)를 테스트하려면, 아래의 순서에 따라 JWT 토큰을 발급받아 인증이 필요

0.  Swagger UI의 `user-controller` > `POST /api/users/signup` API를 통해 회원가입 진행
1.  Swagger UI의 `user-controller` > `POST /api/users/login` API를 통해 로그인하고, 응답으로 받은 JWT 토큰 값을 복사
2.  우측 상단의 `Authorize` 버튼을 클릭
3.  `Value` 입력창에 복사한 **토큰 값만** 붙여넣고 `Authorize` 버튼을 클릭
4.  이제 자물쇠가 잠긴 API들을 정상적으로 테스트 가능
