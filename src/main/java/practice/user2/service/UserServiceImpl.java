package practice.user2.service;

// TODO: import 채우기
//   - lombok.RequiredArgsConstructor
//   - org.springframework.security.crypto.password.PasswordEncoder
//   - org.springframework.stereotype.Service
//   - practice.user2.entity.User
//   - practice.user2.repository.UserRepository
//   - java.util.Optional

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import practice.common.exception.DuplicateResourceException;
import practice.user2.entity.User;
import practice.user2.repository.UserRepository;

import java.util.Optional;

// TODO: 클래스 위 @Service, @RequiredArgsConstructor
@Service("user2ServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // TODO: UserRepository, PasswordEncoder를 private final 필드로
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User signup(String email, String nickname, String password, User.Gender gender) {
    if (userRepository.existsByEmail(email)){
      throw new DuplicateResourceException("이미 가입된 메일 입니다");
    }
    if (userRepository.existsByNickname(nickname)){
      throw new DuplicateResourceException("이미 존재하는 이름입니다");
    }
    String encoderPassword = passwordEncoder.encode(password);

    User user = User.builder()
        .email(email)
        .nickname(nickname)
        .passwordHash(encoderPassword)
        .gender(gender)
        .build();

    return userRepository.save(user);
  }

  @Override
  public User login(String email, String password) {
    Optional<User> userOpt = userRepository.findByEmail(email);

    if (userOpt.isEmpty()){
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"잘못입력되었습니다");
    }
    User user = userOpt.get();
    if (!passwordEncoder.matches(password,user.getPasswordHash())){
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다");
    }
    return user;
  }
  // TODO: signup 구현
    //   1) existsByEmail → true면 예외(IllegalArgumentException)
    //   2) existsByNickname → true면 예외
    //   3) passwordEncoder.encode(password)로 암호화
    //   4) User.builder()로 생성 (passwordHash는 암호화된 값!)
    //   5) save하고 반환

    // TODO: login 구현
    //   1) findByEmail → Optional<User>
    //   2) 비어있으면 예외
    //   3) passwordEncoder.matches(입력비번, 저장된해시) → false면 예외
    //   4) user 반환

}
