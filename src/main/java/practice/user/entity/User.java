package practice.user.entity;

// 연습용 파일입니다.
//
// 필드 (대화에서 같이 정리한 내용):
//   id              : Long, PK, auto-increment
//   email           : String, UNIQUE, NOT NULL
//   nickname        : String, NOT NULL
//   gender          : enum (Gender)
//   passwordHash    : String, nullable (소셜로그인 전용 계정은 비밀번호가 없어서)
//   createdAt       : LocalDateTime, NOT NULL, 기본값 = 생성 시각
//   withdrawnAt     : LocalDateTime, nullable (soft delete 용도)
//   profileImageUrl : String, nullable

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false, unique = false)
  private String nickname;

  @Column(nullable = true, unique = false)
  private String passwordHash;

  @Column(nullable = true, unique = false)
  private String profileImageUrl;

  @Builder.Default
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(nullable = true)
  private LocalDateTime withdrawnAt;

  public enum Gender { MALE, FEMALE }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;
}
