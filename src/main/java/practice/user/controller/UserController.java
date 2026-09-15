package practice.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.user.dto.LoginRequest;
import practice.user.dto.SignupRequest;
import practice.user.entity.User;
import practice.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            User user = userService.signup(
                    request.email(),
                    request.nickname(),
                    request.password(),
                    request.gender()
            );
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: 로그인 엔드포인트 만들기
    //   - @PostMapping("/login")
    //   - @RequestBody LoginRequest request (practice.user.dto.LoginRequest import 필요)
    //   - 반환 타입: ResponseEntity<?>
    //   - 로직:
    //     1) userService.login(request.email(), request.password()) 호출
    //     2) 성공하면 ResponseEntity.ok(유저) 반환
    //     3) 실패(IllegalArgumentException)하면 ResponseEntity.status(401).body(에러메시지) 반환

    @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try {
            User user = userService.login(request.email(), request.password());
            return ResponseEntity.ok(user);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
