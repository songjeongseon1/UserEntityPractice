package practice.user2.dto;

import practice.user2.entity.User;

// TODO: record로 SignupRequest 만들기
//   필드 4개: email(String), nickname(String), password(String), gender(User.Gender)
public record SignupRequest(String email, String nickname, String password, User.Gender gender) {
}