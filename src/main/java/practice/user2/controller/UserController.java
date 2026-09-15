package practice.user2.controller;

// TODO: import 채우기
//   - lombok.RequiredArgsConstructor
//   - org.springframework.http.ResponseEntity
//   - org.springframework.web.bind.annotation.* (PostMapping, RequestBody, RequestMapping, RestController)
//   - practice.user2.dto.SignupRequest, LoginRequest
//   - practice.user2.entity.User
//   - practice.user2.service.UserService

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.user2.dto.UserResponse;
import practice.user2.entity.User;
import practice.user2.dto.LoginRequest;
import practice.user2.dto.SignupRequest;
import practice.user2.service.UserService;

// TODO: 클래스 위 @RestController, @RequestMapping("/api/users2"), @RequiredArgsConstructor
//   (실제 UserController랑 경로 겹치면 안 되니까 /api/users2로)
@RestController("user2Controller")
@RequestMapping("/api/user2")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
    // TODO: UserService를 private final 필드로

    // TODO: 회원가입 엔드포인트
    //   - @PostMapping("/signup"), @RequestBody SignupRequest
    //   - userService.signup(...) 호출, 성공시 ok, 실패(IllegalArgumentException)시 badRequest
  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody SignupRequest request){

      User user = userService.signup(
          request.email(),
          request.nickname(),
          request.password(),
          request.gender()
      );
      return ResponseEntity.ok(UserResponse.from(user));
  }
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request){

      User user = userService.login(request.email(), request.password());
      return ResponseEntity.ok(UserResponse.from(user));
  }
    // TODO: 로그인 엔드포인트
    //   - @PostMapping("/login"), @RequestBody LoginRequest
    //   - userService.login(...) 호출, 성공시 ok, 실패시 status(401)

}
