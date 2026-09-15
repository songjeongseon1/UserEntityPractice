package practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 이 클래스가 앱의 시작점(entry point)이에요. main()을 실행하면 스프링이
// 컴포넌트 스캔(@Service, @RestController 붙은 클래스들을 자동으로 찾아서 등록)을 하고,
// 내장 톰캣 서버를 띄워서 요청을 받을 준비를 해요.
@SpringBootApplication
public class PracticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
    }
}
