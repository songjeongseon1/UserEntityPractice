package practice.user2.dto;

// TODO: import 채우기
//   - practice.user2.entity.User
//   - java.time.LocalDateTime

import practice.user2.entity.User;

import java.time.LocalDateTime;

// 이 record는 signup/login 응답으로 "클라이언트한테 보여줘도 되는 정보"만 담습니다.
// passwordHash, withdrawnAt처럼 내부용/민감 정보는 여기 넣지 않습니다.
public record UserResponse(Long id, String email, String nickname, User.Gender gender, LocalDateTime createdAt) {
  public static UserResponse from(User user){
    return new UserResponse(
        user.getId(),
        user.getEmail(),
        user.getNickname(),
        user.getGender(),
        user.getCreatedAt()
    );
  }
}
// TODO: record로 UserResponse 만들기
//   필드 5개: id(Long), email(String), nickname(String), gender(User.Gender), createdAt(LocalDateTime)
//   (profileImageUrl은 넣어도 되고 빼도 됨 - 판단해서 결정)

// TODO: static factory method 만들기
//   public static UserResponse from(User user) {
//       User 엔티티를 받아서 UserResponse를 만들어 반환
//       (User의 getter들을 이용해서 필드 하나하나 꺼내옴)
//   }
