package practice.user.entity;

// 연습용 파일입니다. User 엔티티 만들 때랑 패턴은 같고, 이번엔 연관관계(@ManyToOne)가 새로 추가돼요.
//
// 필드:
//   id     : Long, PK, auto-increment
//   user   : User, 연관관계 (@ManyToOne, fetch=LAZY, @JoinColumn(name="user_id"))
//   energy : Double, 에너지 축 점수
//   vibe   : Double, 분위기 축 점수
//   depth  : Double, 깊이 축 점수
//
// 클래스 위 애노테이션은 User 엔티티 때랑 똑같은 조합을 쓰면 돼요
// (@Entity, @Table, @Getter, @NoArgsConstructor(PROTECTED), @AllArgsConstructor(PRIVATE), @Builder)

// TODO: import 채우기 (jakarta.persistence.*, lombok.*)

import jakarta.persistence.*;
import lombok.*;

// TODO: 클래스 위 애노테이션 붙이기 (@Entity, @Table(name="user_styles"), @Getter,
//       @NoArgsConstructor(PROTECTED), @AllArgsConstructor(PRIVATE), @Builder)
@Entity
@Table(name = "user_styles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserStyle {

  // TODO: id 필드 (@Id, @GeneratedValue)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  // TODO: user 필드 (@ManyToOne(fetch=LAZY), @JoinColumn(name="user_id"))
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  // TODO: energy, vibe, depth 필드 (Double, @Column)
  @Column
  private Double energy;
  @Column
  private Double vibe;
  @Column
  private Double depth;

  public static UserStyle create(User user){
    return UserStyle.builder()
        .user(user)
        .build();
  }

  public void applyOnboarding(Double energy,Double vibe,Double depth){
    this.energy=energy;
    this.vibe=vibe;
    this.depth=depth;
  }
}
