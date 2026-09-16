package practice.couple.entity;

// 연습용 파일입니다. 아래 TODO를 채워서 완성해보세요.
//
// 필드:
//   id          : Long, PK, auto-increment
//   inviter     : User, 초대를 보낸 사람 (@ManyToOne)
//   invitee     : User, 초대를 받은 사람 (@ManyToOne)
//   status      : enum (CoupleStatus - PENDING, ACCEPTED, REJECTED), NOT NULL
//   requestedAt : LocalDateTime, NOT NULL, 기본값 = 생성 시각
//   respondedAt : LocalDateTime, nullable (아직 응답 안 했으면 null)

// TODO: import 채우기 (jakarta.persistence.*, lombok.*, java.time.LocalDateTime, practice.user2.entity.User)

import jakarta.persistence.*;
import lombok.*;
import practice.user2.entity.User;

import java.time.LocalDateTime;

// TODO: 클래스 위 애노테이션 (@Entity, @Table(name="couples"), @Getter,
//       @NoArgsConstructor(PROTECTED), @AllArgsConstructor(PRIVATE), @Builder)
@Entity
@Table(name = "couples")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Couple {

  // TODO: id 필드 (@Id, @GeneratedValue(IDENTITY))
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  // TODO: inviter 필드
  //   - @ManyToOne(fetch = FetchType.LAZY)
  //   - @JoinColumn(name = "inviter_id")
  //   - 힌트: UserStyle에서 @ManyToOne 한 번 써봤음. 이번엔 같은 User 타입을 가리키는
  //     필드가 두 개라서, @JoinColumn으로 컬럼 이름을 각각 다르게 지정해야
  //     Hibernate가 두 관계를 구분할 수 있음.
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inviter_id")
  private User inviter;


  // TODO: invitee 필드
  //   - @ManyToOne(fetch = FetchType.LAZY)
  //   - @JoinColumn(name = "invitee_id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "invitee_id")
  private User invitee;

  // TODO: status 필드 + CoupleStatus enum
  //   - enum CoupleStatus { PENDING, ACCEPTED, REJECTED }
  //   - @Enumerated(EnumType.STRING)
  //   - @Column(nullable = false)
  public enum CoupleStatus{PENDING,ACCEPTED,REJECTED}
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CoupleStatus status;

  // TODO: requestedAt 필드
  //   - @Builder.Default
  //   - @Column(nullable = false, updatable = false)
  //   - 기본값 = LocalDateTime.now()
  @Builder.Default
  @Column(nullable = false,updatable = false)
  private LocalDateTime requestedAt = LocalDateTime.now();

  // TODO: respondedAt 필드
  //   - @Column(nullable = true)

  @Column(nullable = true)
  private LocalDateTime respondedAt;

  // TODO: accept() 메서드
  //   - status를 CoupleStatus.ACCEPTED로 바꾸기
  //   - respondedAt을 LocalDateTime.now()로 설정하기
  //   - 힌트: UserStyle.applyOnboarding()이랑 똑같은 패턴.
  //     Service에서 @Transactional 안에서 이 메서드 호출하면
  //     따로 save() 안 해도 변경 감지(dirty checking)로 자동 반영됨.
  public void accept(){
    this.status = CoupleStatus.ACCEPTED;
    this.respondedAt = LocalDateTime.now();
  }
  // TODO: reject() 메서드
  //   - status를 CoupleStatus.REJECTED로 바꾸기
  //   - respondedAt을 LocalDateTime.now()로 설정하기
  public void reject(){
    this.status = CoupleStatus.REJECTED;
    this.respondedAt = LocalDateTime.now();
  }
}
