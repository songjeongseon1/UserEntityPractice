package practice.user.service;

import practice.user.entity.User;

public interface UserService {
  User signup(String email, String nickname, String password, User.Gender gender);
  User login(String email,String password);
    // TODO: 로그인 메서드 시그니처도 추가하세요.
    //   파라미터: email(String), password(String)
    //   반환 타입: User (로그인 성공한 유저)

}
