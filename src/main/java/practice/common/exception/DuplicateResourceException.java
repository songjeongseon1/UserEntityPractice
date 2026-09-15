package practice.common.exception;

// 회원가입 시 이메일/닉네임이 이미 존재할 때처럼,
// "이미 있는 자원을 또 만들려고 할 때" 던지는 전용 예외.
// RuntimeException을 상속해서 unchecked 예외로 만든다 (throws 선언 강제 안 됨).
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
