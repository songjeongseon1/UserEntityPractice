package practice.user.repository;

import practice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);

  long countByWithdrawnAtIsNull();

  List<User> findByWithdrawnAtBefore(LocalDateTime cutoff);
}
