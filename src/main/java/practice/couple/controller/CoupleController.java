package practice.couple.controller;

// TODO: import 채우기
//   - lombok.RequiredArgsConstructor
//   - org.springframework.http.ResponseEntity
//   - org.springframework.web.bind.annotation.* (PathVariable, PostMapping, PutMapping, RequestMapping, RequestParam, RestController)
//   - practice.couple.entity.Couple
//   - practice.couple.service.CoupleService

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.couple.dto.CoupleResponse;
import practice.couple.entity.Couple;
import practice.couple.service.CoupleService;

// TODO: 클래스 위 @RestController, @RequestMapping("/api/couples"), @RequiredArgsConstructor
@RestController
@RequestMapping("/api/couples")
@RequiredArgsConstructor
public class CoupleController {

    // TODO: CoupleService를 private final 필드로
  private final CoupleService coupleService;
    // TODO: 초대 보내기 엔드포인트
    //   - @PostMapping("/invite")
    //   - 파라미터: @RequestParam Long inviterId, @RequestParam Long inviteeId
    //     힌트: URL이 "/api/couples/invite?inviterId=1&inviteeId=2" 형태가 됨 (경로에 없고 ? 뒤에 붙음)
    //   - coupleService.invite(...) 호출, 결과를 ResponseEntity.ok(...)로 리턴
    //   - try-catch 필요 없음 (IllegalArgumentException/DuplicateResourceException/ResponseStatusException 전부
    //     GlobalExceptionHandler가 처리 — 단, IllegalArgumentException 전용 핸들러가 있는지는 한번 확인해볼 것)
  @PostMapping("/invite")
  public ResponseEntity<?> invite(@RequestParam Long inviterId,@RequestParam Long inviteeId){
    Couple couple = coupleService.invite(inviterId,inviteeId);
    return ResponseEntity.ok(CoupleResponse.from(couple));
  }
    // TODO: 수락 엔드포인트
    //   - @PutMapping("/{coupleId}/accept")
    //   - 파라미터: @PathVariable Long coupleId, @RequestParam Long inviteeId
    //     힌트: URL이 "/api/couples/5/accept?inviteeId=2" 형태가 됨 (coupleId는 경로 일부라 @PathVariable)
    //   - coupleService.accept(...) 호출, 결과를 ResponseEntity.ok(...)로 리턴
  @PutMapping("/{coupleId}/accept")
  public ResponseEntity<?> accept(@PathVariable Long coupleId,@RequestParam Long inviteeId){
    Couple couple = coupleService.accept(coupleId, inviteeId);
    return ResponseEntity.ok(CoupleResponse.from(couple));
  }
    // TODO: 거절 엔드포인트
    //   - @PutMapping("/{coupleId}/reject")
    //   - accept랑 동일한 파라미터 패턴
  @PutMapping("/{coupleId}/reject")
  public ResponseEntity<?> reject(@PathVariable Long coupleId,@RequestParam Long inviteeId){
    Couple couple = coupleService.reject(coupleId, inviteeId);
    return ResponseEntity.ok(CoupleResponse.from(couple));
  }
}
