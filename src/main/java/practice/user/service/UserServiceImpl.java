package practice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practice.user.entity.User;
import practice.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signup(String email, String nickname, String password, User.Gender gender) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다");
        }
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다");
        }
        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .passwordHash(encodedPassword)
                .gender(gender)
                .build();

        return userRepository.save(user);
    }

    // TODO: 로그인 메서드 구현 (@Override 붙이기)
    //   1) userRepository.findByEmail(email)로 Optional<User> 받기
    //   2) 비어있으면(isEmpty()) 예외 던지기
    //      (throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다");)
    //   3) Optional에서 User 꺼내기 (.get())
    //   4) passwordEncoder.matches(입력받은password, user.getPasswordHash())로 비밀번호 확인
    //      - false면 예외 던지기 (2번이랑 같은 메시지 - 이메일/비번 실패 구분 안 함)
    //   5) 다 통과하면 user 반환

    @Override
    public User login(String email,String password){
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()){
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다");
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(password,user.getPasswordHash())){
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다");
        }
        return user;
    }

}
