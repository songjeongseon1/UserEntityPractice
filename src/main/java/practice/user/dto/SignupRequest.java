package practice.user.dto;

import practice.user.entity.User;

public record SignupRequest(String email, String nickname, User.Gender gender, String password) {
}
