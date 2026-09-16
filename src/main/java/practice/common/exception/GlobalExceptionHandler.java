package practice.common.exception;

// TODO: import 채우기
//   - org.springframework.http.HttpStatus
//   - org.springframework.http.ResponseEntity
//   - org.springframework.web.bind.annotation.ExceptionHandler
//   - org.springframework.web.bind.annotation.RestControllerAdvice


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

// TODO: 클래스 위 @RestControllerAdvice
//   ("이 클래스는 앱 전체 Controller에서 터지는 예외를 대신 처리한다"는 선언)
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<?> handleDuplicateResource(DuplicateResourceException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exception(Exception e){
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다");
  }
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<?> handleResponseStatus(ResponseStatusException e){
    return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
  }
    // TODO: DuplicateResourceException 핸들러
    //   - @ExceptionHandler(DuplicateResourceException.class)
    //   - public ResponseEntity<?> methodName(DuplicateResourceException e)
    //   - ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) 리턴

    // TODO: (선택) 그 외 예상 못한 모든 예외를 잡는 catch-all 핸들러
    //   - @ExceptionHandler(Exception.class)
    //   - status는 INTERNAL_SERVER_ERROR
    //   - body에 e.getMessage()를 그대로 노출하는 게 왜 위험할 수 있는지 생각해보고,
    //     대신 고정된 안내 메시지("서버 내부 오류가 발생했습니다" 등)를 넣어보기

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }
}
