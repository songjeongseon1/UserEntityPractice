package practice.user2.service;

// TODO: import practice.user2.entity.User

import practice.user2.entity.User;

public interface UserService {
User signup(String email,String nickname,String password,User.Gender gender);
User login(String email,String password);
    // TODO: signup 시그니처 - email, nickname, password, gender(User.Gender) 받아서 User 반환

    // TODO: login 시그니처 - email, password 받아서 User 반환

}
