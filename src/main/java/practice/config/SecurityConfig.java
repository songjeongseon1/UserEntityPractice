package practice.config;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration: "이 클래스 안에 있는 @Bean 메서드들의 결과를 스프링이 관리하는 객체로 등록해라"
// PasswordEncoder는 인터페이스라 스프링이 자동으로 구현체를 고를 수 없어서, 여기서 직접 알려줘요.
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Boot 4.1부터 H2 콘솔 자동 설정이 spring-boot-autoconfigure에서 빠져서
    // (application.yaml의 spring.h2.console.* 설정이 더 이상 아무 효과가 없음),
    // H2가 원래 제공하는 서블릿을 직접 등록해서 /h2-console/* 경로로 열어준다.
    @Bean
    public ServletRegistrationBean<JakartaWebServlet> h2ConsoleServlet() {
        return new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2-console/*");
    }
}
