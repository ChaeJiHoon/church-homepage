package com.church.church_homepage.repository;

import com.church.church_homepage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


// 데이터 베이스에 접근하는 모든 코드는 리포지토리 계층에 모이게 된다.
// 나중 DB 관련 로직을 수행할 때 이 리포지토리만 보면 된다! - 관심사의 분리


// 클래스가 아닌 인터페이스로 정의한 이유
// 설계도인 인터페이스를 통해 Spring이 알아서 실제 구현체를 만들어준다 <- 제일 중요함

// 우리가 UserRepository 인터페이스를 정의하면, 애플리케이션이 실행될 떄 Spring Data JPA가 이 인터페이스를 읽어들인다.
// 그리고 `JpaRepository`가 가진 모든 기본 CRUD 기능과, 우리가 새로 정의한 메소드(`findByUsername` 등)들을 실제로 구현한
// 클래스(프록시 객체)를 동적으로 생성해서 스프링 컨테이너에 등록
// 개발자는 나중에 이 UserRepository를 주입받아 사용하기만 하면, Spring이 만들어놓은 그 구현체를 사용하게 되는 것!

// ***** 결론: 우리는 "어떻게(How)" 만들지 고민할 필요 없이, "무엇(What)"이 필요한지만 인터페이스에 정의하면 된다. -> 엄청난 생산성 향상

public interface UserRepository extends JpaRepository<User,Long> {

    // 1. username으로 사용자를 찾는 메소드
    Optional<User> findByUsername(String username);

    // 2. email로 사용자를 찾는 메소드
    boolean existsByEmail(String email);
}

