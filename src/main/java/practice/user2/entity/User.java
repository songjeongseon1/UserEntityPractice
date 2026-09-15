package practice.user2.entity;

// 연습용 파일입니다. 아래 TODO를 채워서 완성해보세요.
//
// 필드:
//   id              : Long, PK, auto-increment
//   email           : String, UNIQUE, NOT NULL
//   nickname        : String, NOT NULL
//   gender          : enum (Gender - MALE, FEMALE)
//   passwordHash    : String, nullable (왜 nullable이어야 하는지 생각해보기)
//   createdAt       : LocalDateTime, NOT NULL, 기본값 = 생성 시각
//   withdrawnAt     : LocalDateTime, nullable (soft delete 용도)
//   profileImageUrl : String, nullable

// TODO: import 채우기 (jakarta.persistence.*, lombok.*, java.time.LocalDateTime)

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// TODO: 클래스 위 애노테이션 (@Entity, @Table(name="users"), @Getter,
//       @NoArgsConstructor(PROTECTED), @AllArgsConstructor(PRIVATE), @Builder)
@Entity(name = "User2")
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User {

  // TODO: 필드 8개 + 컬럼 애노테이션 + Gender enum
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false,unique = true)
  private String email;

  @Column(nullable = false,unique = false)
  private String nickname;

  @Column(nullable = true,unique = false)
  private String passwordHash;

  @Column(nullable = true, unique = false)
  private String profileImageUrl;

  public enum Gender{MALE,FEMALE}
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;

  @Builder.Default
  @Column(nullable = false,updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(nullable = true)
  private LocalDateTime withdrawnAt;
  // TODO: 정적 팩토리 메서드는 없어도 됨 (User는 Builder로 직접 만듦)

}
