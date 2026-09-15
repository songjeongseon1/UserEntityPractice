package practice.user2.repository;

// TODO: import 채우기
//   - practice.user2.entity.User
//   - org.springframework.data.jpa.repository.JpaRepository
//   - java.time.LocalDateTime, java.util.List, java.util.Optional

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.user2.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// TODO: JpaRepository<User, Long> 상속
@Repository("user2Repository")
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);

  long countByWithdrawnAtIsNull();

  List<User> findByWithdrawnAtBefore(LocalDateTime cutoff);
    // TODO 1: 이메일로 유저 찾기 - Optional<User>
    // TODO 2: 이메일 존재 여부 - boolean
    // TODO 3: 닉네임 존재 여부 - boolean
    // TODO 4: 탈퇴 안 한 유저 수 세기 - long, countBy + 필드명 + IsNull
    // TODO 5: 특정 시각 이전에 탈퇴한 유저 목록 - List<User>, findBy + 필드명 + Before

}
