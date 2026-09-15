package practice.user.controller;

// TODO: import 채우기
//   - lombok.RequiredArgsConstructor
//   - org.springframework.http.ResponseEntity
//   - org.springframework.web.bind.annotation.* (PathVariable, PutMapping, RequestBody, RequestMapping, RestController)
//   - practice.user.dto.OnboardingRequest
//   - practice.user.entity.UserStyle
//   - practice.user.service.UserStyleService

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.user.dto.OnboardingRequest;
import practice.user.service.UserStyleService;

// TODO: 클래스 위에 @RestController, @RequestMapping("/api/users"), @RequiredArgsConstructor 붙이기
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserStyleController {

    // TODO: UserStyleService를 private final 필드로 선언
    private final UserStyleService userStyleService;
    // TODO: 온보딩 저장 엔드포인트 만들기
    //   - @PutMapping("/{userId}/onboarding")
    //     ('/' 뒤에 {userId}처럼 중괄호로 감싸면, 그 부분이 변수처럼 동작하는 "경로 변수"가 돼요)
  @PutMapping("/{userId}/onboarding")
  public ResponseEntity<?> saveOnboarding(@PathVariable Long userId, @RequestBody OnboardingRequest request){
    return ResponseEntity.ok(userStyleService.saveOnboarding(userId, request.energy(), request.vibe(), request.depth()));
  }
    //   - 메서드 파라미터 2개:
    //       @PathVariable Long userId   ← URL의 {userId} 부분이 여기로 들어옴
    //       @RequestBody OnboardingRequest request   ← JSON body
    //   - 반환 타입: ResponseEntity<?>
    //   - 로직: userStyleService.saveOnboarding(userId, request.energy(), request.vibe(), request.depth())
    //     호출하고 결과를 ResponseEntity.ok(...)로 반환
    //   - try-catch가 필요 없어요! 왜인지 기억나시나요? (힌트: ResponseStatusException)

}
