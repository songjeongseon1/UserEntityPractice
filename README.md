# UserEntityPractice

Date Manager 백엔드의 User 패키지 구조(Entity → Repository → DTO → Service → Controller)를 처음부터 직접 만들어보는 연습용 Spring Boot 프로젝트입니다.

## 구성

- `practice.user` / `practice.user2` — User, UserStyle 도메인 (회원가입, 로그인, 온보딩)
- `practice.couple` — 두 User를 짝짓는 Couple 도메인 (초대/수락/거절)
- `practice.common.exception` — 전역 예외 처리 (`@RestControllerAdvice`)

## 실행

```bash
./gradlew bootRun
```

H2 인메모리 DB를 사용합니다.
