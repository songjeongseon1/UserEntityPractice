package practice.user.repository;

// TODO: import 채우기
//   - practice.user.entity.UserStyle
//   - org.springframework.data.jpa.repository.JpaRepository
//   - java.util.Optional

import org.springframework.data.jpa.repository.JpaRepository;
import practice.user.entity.UserStyle;

import java.util.Optional;

// TODO: JpaRepository<UserStyle, Long> 상속
public interface UserStyleRepository extends JpaRepository<UserStyle, Long> {
  // TODO 1: 유저 id로 성향 찾기 - 관계(user)를 통과해서 조회
  //   메서드명 힌트: findBy + User + Id (관계 필드명 + 그 안의 필드명을 이어붙임)
  //   반환 타입: Optional<UserStyle>
  Optional<UserStyle> findByUserId(Long userid);

  boolean existsByUserId(Long userId);

}
