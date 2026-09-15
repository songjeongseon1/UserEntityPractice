# practice.user2 복습 노트

Entity → Repository → DTO → Service → Controller 계층 구조 복습.

## 전체 흐름

```
[클라이언트] → Controller → Service → Repository → [DB]
                    ↑            ↑
              DTO(Request/Response)  Entity
```

요청이 들어오면 Controller가 받아서 Service한테 넘기고, Service가 실제 비즈니스 로직(검증, 암호화 등)을 처리한 뒤 Repository를 통해 DB에 접근한다. 각 계층은 자기 역할만 하고 다른 계층 일에 관여하지 않는다.

---

## 1. Entity — `User.java`

```java
@Entity(name = "User2")
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User { ... }
```

- DB 테이블 구조를 그대로 나타내는 클래스. `@Id`, `@Column`으로 컬럼 제약을, `@Enumerated(STRING)`으로 enum을 문자열로 저장하도록 매핑.
- `@NoArgsConstructor(PROTECTED)` + `@AllArgsConstructor(PRIVATE)` + `@Builder` 조합: JPA는 스펙상 기본 생성자가 꼭 필요하지만, 외부 코드에서 `new User()`로 막 만드는 건 막고 싶어서 `protected`로 잠그고, 오직 `User.builder()...build()`로만 만들게 강제하는 패턴.
- `@Entity(name="User2")`는 JPA 내부 식별자(엔티티 이름) 충돌을 피하려고 명시적으로 지정한 것. (아래 "트러블슈팅" 참고)

## 2. Repository — `UserRepository.java`

```java
@Repository("user2Repository")
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
  boolean existsByNickname(String nickname);
  long countByWithdrawnAtIsNull();
  List<User> findByWithdrawnAtBefore(LocalDateTime cutoff);
}
```

- 인터페이스만 선언하면 Spring Data JPA가 부팅 시점에 구현체(프록시)를 자동 생성한다.
- `save`, `findById` 같은 기본 CRUD는 `JpaRepository` 상속만으로 얻고, `findByEmail`처럼 직접 선언한 메서드는 메서드 이름을 파싱해서 자동으로 쿼리를 만들어준다 (`findBy`/`existsBy`/`countBy` + 필드명 + `IsNull`/`Before` 등).

## 3. DTO — `SignupRequest`, `LoginRequest`, `UserResponse`

- **Request DTO** (`SignupRequest`, `LoginRequest`): 클라이언트 → 서버로 들어오는 입력값 전용. `record`라서 불변(immutable)이고 생성자/getter/equals가 자동 생성됨.
- **Response DTO** (`UserResponse`): 서버 → 클라이언트로 나가는 출력값 전용. `from(User user)` 정적 팩토리 메서드로 Entity를 변환해서, `passwordHash` 같은 민감 정보는 빼고 필요한 필드만 내보냄.

```java
public record UserResponse(Long id, String email, String nickname, User.Gender gender, LocalDateTime createdAt) {
  public static UserResponse from(User user) {
    return new UserResponse(
        user.getId(), user.getEmail(), user.getNickname(),
        user.getGender(), user.getCreatedAt()
    );
  }
}
```

- 핵심 원칙: **Entity는 DB용, DTO는 API 계약용** — 절대 같은 클래스를 두 역할에 같이 쓰지 않는다.
- 왜 분리하나:
  1. **보안** — Entity를 그대로 리턴하면 `passwordHash`, `withdrawnAt` 같은 내부 정보까지 노출된다.
  2. **결합도 분리** — Entity(DB 내부 구조)와 API 응답(외부 계약)이 서로 영향을 안 주게 한다. DB 컬럼을 추가해도 API 응답이 자동으로 바뀌지 않고, API 응답 모양을 바꿔도 DB 컬럼명을 안 건드려도 된다.
  3. **연관관계 직렬화 문제 예방** — `@ManyToOne` 같은 연관관계가 있는 엔티티를 그대로 리턴하면 `LazyInitializationException`이나 순환참조 문제가 생길 수 있는데, DTO로 필요한 필드만 꺼내면 이 문제 자체가 없다.
- 정적 팩토리 메서드 이름 관례: `from(X)`는 1:1 변환, `of(...)`는 여러 값 조합, `valueOf(...)`는 문자열 등에서 변환할 때 흔히 씀.

## 4. Service — `UserService`(인터페이스) + `UserServiceImpl`

```java
@Service("user2ServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User signup(...) {
    // 1) existsByEmail / existsByNickname → 중복이면 예외
    // 2) passwordEncoder.encode(password)로 암호화
    // 3) User.builder()로 생성
    // 4) save
  }

  public User login(...) {
    // 1) findByEmail → Optional<User>
    // 2) 비어있으면 예외
    // 3) passwordEncoder.matches(입력비번, 저장된해시) → false면 예외
    // 4) user 반환
  }
}
```

- 실제 비즈니스 로직이 여기 있다.
- 인터페이스(`UserService`)와 구현체(`UserServiceImpl`)를 나눈 이유: Controller가 **구체적인 구현이 아니라 "무엇을 할 수 있는지"(인터페이스)에만 의존**하게 하기 위함 — DI의 핵심 원칙.

## 5. Controller — `UserController.java`

```java
@RestController("user2Controller")
@RequestMapping("/api/user2")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
    try {
      User user = userService.signup(...);
      return ResponseEntity.ok(UserResponse.from(user));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
```

- HTTP 요청/응답만 다루는 얇은 계층. 실제 로직은 없고, Service 호출 → 결과를 DTO로 감싸서 리턴, 예외를 HTTP 상태 코드로 변환하는 게 전부.

## 의존성 주입(DI) 흐름

- **빈이 만들어지는 순서**: Repository(자동 생성) → Service(Repository 주입받아 생성) → Controller(Service 주입받아 생성)
- **실제 호출되는 순서**: 반대로 Controller → Service → Repository
- `@RequiredArgsConstructor`(생성자 주입)로 연결되고, Spring 컨테이너가 타입에 맞는 빈을 찾아 자동으로 꽂아준다.

---

## 오늘 겪은 트러블슈팅 3종 (같은 원리의 반복)

`practice.user2`가 기존 `practice.user`와 클래스 이름이 같아서 생긴 문제들. 공통 원인: **Spring/JPA는 패키지가 달라도 기본적으로 클래스의 simple name만 보고 내부 식별자를 정한다.**

| 계층 | 에러 | 해결 |
|---|---|---|
| Controller/Service (Spring 빈) | `ConflictingBeanDefinitionException` — 빈 이름 `userController`가 겹침 | `@RestController("user2Controller")`, `@Service("user2ServiceImpl")`로 명시적 이름 지정 |
| Repository (Spring Data JPA) | 빈 이름 `userRepository`가 겹침, overriding 비활성화라 부팅 실패 | `@Repository("user2Repository")` 추가 |
| Entity (JPA/Hibernate) | `DuplicateMappingException` — 엔티티 이름 `User`가 겹침 | `@Entity(name = "User2")`로 엔티티 이름 명시 |

- `@Table(name="users")`는 **DB 테이블 이름**, `@Entity(name=...)`은 **JPA/Hibernate 내부에서 쓰는 엔티티 이름** — 서로 다른 개념이라 안 겹쳐도 됨. (실제로 `user`, `user2` 두 엔티티가 같은 `users` 테이블을 같이 씀 — 컬럼 구조가 같아서 문제없이 동작함)
- 원래 `@Repository`의 본래 목적은 DB 예외(`SQLException` 등)를 Spring의 `DataAccessException` 계열로 자동 변환해주는 것. Spring Data JPA 리포지토리는 이 변환이 내부적으로 이미 처리돼 있어서(`SimpleJpaRepository` 자체에 `@Repository`가 붙어있음) 원래는 우리가 직접 안 붙여도 된다. 여기선 순수하게 **빈 이름 지정 용도**로 빌려 쓴 것.

---

## API 응답 변화 (실제 확인한 결과)

**DTO 적용 전** (`User` 엔티티 그대로 리턴):
```json
{"createdAt":"...","email":"test@test.com","gender":"MALE","id":1,"nickname":"tester","passwordHash":"$2a$10$...","profileImageUrl":null,"withdrawnAt":null}
```

**DTO 적용 후** (`UserResponse.from(user)`):
```json
{"id":1,"email":"test3@test.com","nickname":"tester3","gender":"MALE","createdAt":"..."}
```

---

## 6. 전역 예외 처리 — `@RestControllerAdvice` / `@ExceptionHandler`

### 문제: Controller마다 try-catch가 반복됨

엔드포인트가 늘어날수록 각 메서드에 `try-catch`를 복붙해야 하는 문제가 있었음.

### 해결: 예외 처리를 한 곳으로 모음

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<?> handleDuplicateResource(DuplicateResourceException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<?> handleResponseStatus(ResponseStatusException e){
    return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exception(Exception e){
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다");
  }
}
```

- `@RestControllerAdvice`: "이 클래스가 앱 전체 `@RestController`에서 터지는 예외를 대신 처리한다"는 선언. `@ControllerAdvice` + `@ResponseBody` 조합 (`@RestController` = `@Controller` + `@ResponseBody`인 것과 같은 패턴).
- `@ExceptionHandler(타입.class)`: 그 타입의 예외가 앱 어디서 터지든 이 메서드가 대신 호출됨. Controller에서 `try-catch`가 필요 없어짐.
- 메서드 이름은 뭐든 상관없음 — Spring은 메서드 이름이 아니라 `@ExceptionHandler`에 지정한 **타입**으로 매칭한다.

### 예외 타입을 어떻게 나눴나 (설계 이유)

같은 `IllegalArgumentException`을 signup(400 의도)과 login(401 의도) 두 군데서 다른 의미로 쓰고 있었는데, 전역 핸들러는 **타입으로만** 구분하기 때문에 이대로는 상태 코드를 구분할 수 없었음. 그래서:

- **`DuplicateResourceException`** (직접 만든 커스텀 예외, `RuntimeException` 상속) — signup 중복 체크용. `GlobalExceptionHandler`에 `@ExceptionHandler`로 직접 등록해서 400에 매핑.
- **`ResponseStatusException`** (Spring이 이미 제공하는 클래스) — login 인증 실패용. 예외 자신이 상태코드(`HttpStatus.UNAUTHORIZED`)와 메시지를 직접 들고 있어서, Spring이 원래 자동으로 그 상태코드로 응답해줌 (`ResponseStatusExceptionResolver`가 내부적으로 처리).

**둘 중 뭘 고를지 기준**: 그 에러가 앱 여러 곳에서 반복될 가능성이 높으면 → 커스텀 예외 + 전역 핸들러로 관리. 지금 여기서만 쓰는 상태코드면 → `ResponseStatusException`으로 바로 던지는 게 더 간단.

### Spring이 원래 아는 예외 vs 모르는 예외

| 예외 타입 | Spring이 아는가? | 처리 방법 |
|---|---|---|
| `IllegalArgumentException` (일반 Java 예외) | 모름 → 기본 500 | `try-catch`로 직접 통역하거나 `@ExceptionHandler`로 등록해야 함 |
| `ResponseStatusException` (Spring 전용 예외) | 앎 (상태코드를 스스로 담고 있음) | 아무것도 안 해도 Spring이 알아서 처리 |
| `DuplicateResourceException` (직접 만든 예외) | 원래는 모름 | `GlobalExceptionHandler`에 등록해서 Spring한테 직접 가르쳐줌 |

### 실전에서 겪은 버그: catch-all이 ResponseStatusException까지 가로챔

`@ExceptionHandler(Exception.class)` catch-all을 추가했더니, 원래 Spring이 알아서 401로 처리해주던 `ResponseStatusException`까지 이 catch-all이 먼저 잡아버려서 **401이 아니라 500**이 나가는 버그가 실제로 발생함 (`ResponseStatusException`도 결국 `Exception`의 하위 타입이라 매칭돼버림).

**해결**: `ResponseStatusException` 전용 핸들러를 추가해서 `Exception.class`보다 더 구체적인 타입으로 먼저 잡히게 함 — Spring은 여러 핸들러가 매칭 가능할 때 **가장 구체적인 타입**을 우선시한다.

### 최종 확인 결과 (curl 테스트)

| 시나리오 | 던지는 예외 | 응답 |
|---|---|---|
| 정상 가입 | - | 200 |
| 중복 이메일 가입 | `DuplicateResourceException` | 400 `"이미 가입된 메일 입니다"` |
| 로그인 비밀번호 틀림 | `ResponseStatusException` | 401 `"비밀번호가 일치하지 않습니다"` |
| 로그인 정상 | - | 200 |
