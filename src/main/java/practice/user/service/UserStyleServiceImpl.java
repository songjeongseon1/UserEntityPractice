package practice.user.service;

// TODO: import 채우기
//   - lombok.RequiredArgsConstructor
//   - org.springframework.http.HttpStatus
//   - org.springframework.stereotype.Service
//   - org.springframework.transaction.annotation.Transactional
//   - org.springframework.web.server.ResponseStatusException
//   - practice.user.entity.User
//   - practice.user.entity.UserStyle
//   - practice.user.repository.UserRepository
//   - practice.user.repository.UserStyleRepository

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import practice.user.entity.User;
import practice.user.entity.UserStyle;
import practice.user.repository.UserRepository;
import practice.user.repository.UserStyleRepository;

// TODO: 클래스 위에 @Service, @RequiredArgsConstructor 붙이기
@Service
@RequiredArgsConstructor
public class UserStyleServiceImpl implements UserStyleService {
  private final UserRepository userRepository;
  private final UserStyleRepository userStyleRepository;
  @Override
  @Transactional
  public UserStyle saveOnboarding(Long userId, Double energy, Double vibe, Double depth) {
    User user = userRepository.findById(userId)
        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다"));
    UserStyle style = userStyleRepository.findByUserId(userId)
        .orElseGet(()->UserStyle.create(user));

    style.applyOnboarding(energy,vibe,depth);

    return userStyleRepository.save(style);
  }

  // TODO: UserRepository, UserStyleRepository를 private final 필드로 선언

    // TODO: saveOnboarding 구현 (@Override, @Transactional 둘 다 붙이기)
    //   1) userRepository.findById(userId)로 User 찾기
    //      없으면: throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다");
    //   2) userStyleRepository.findByUserId(userId)
    //        .orElseGet(() -> UserStyle.create(user))
    //      로 기존 성향 있으면 그거, 없으면 새로 생성
    //   3) style.applyOnboarding(energy, vibe, depth) 호출
    //   4) userStyleRepository.save(style)하고 그 결과 반환

}
