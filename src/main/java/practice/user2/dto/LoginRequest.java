package practice.user2.dto;

// TODO: record로 LoginRequest 만들기
//   필드 2개: email(String), password(String)
public record LoginRequest(String email,String password) {
}