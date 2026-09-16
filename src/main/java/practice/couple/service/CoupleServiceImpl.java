package practice.couple.service;

// TODO: import 채우기
//   - lombok.RequiredArgsConstructor
//   - org.springframework.http.HttpStatus
//   - org.springframework.stereotype.Service
//   - org.springframework.transaction.annotation.Transactional
//   - org.springframework.web.server.ResponseStatusException
//   - practice.common.exception.DuplicateResourceException
//   - practice.couple.entity.Couple
//   - practice.couple.repository.CoupleRepository
//   - practice.user2.entity.User
//   - practice.user2.repository.UserRepository

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import practice.common.exception.DuplicateResourceException;
import practice.couple.entity.Couple;
import practice.couple.repository.CoupleRepository;
import practice.user2.entity.User;
import practice.user2.repository.UserRepository;

// TODO: 클래스 위 @Service, @RequiredArgsConstructor
@Service
@RequiredArgsConstructor
public class CoupleServiceImpl implements CoupleService {

    // TODO: CoupleRepository, UserRepository를 private final 필드로
    //   (User를 조회해야 Couple.builder().inviter(user)...로 엮을 수 있음)
  private final CoupleRepository coupleRepository;
  private final UserRepository userRepository;
    // TODO: invite 구현
    //   1) inviterId랑 inviteeId가 같으면 예외 (자기 자신 초대 방지)
    //      - IllegalArgumentException 던지기
    //   2) existsByInviterIdAndInviteeId로 이미 초대한 적 있는지 체크
    //      - 있으면 DuplicateResourceException
    //   3) userRepository.findById(inviterId) → Optional
    //      - 없으면 ResponseStatusException(HttpStatus.NOT_FOUND, "...")
    //   4) userRepository.findById(inviteeId)도 마찬가지로 조회
    //   5) Couple.builder()로 생성 (status는 PENDING)
    //      - 힌트: status 필드는 @Builder.Default가 안 붙어있으니 builder에서 직접 지정해야 함
    //   6) coupleRepository.save()하고 반환
  @Override
  public Couple invite(Long inviterId,Long inviteeId){
    if (inviterId.equals(inviteeId)){
      throw new IllegalArgumentException("자기 자신을 초대할 수 없습니다.");
    }
    if (coupleRepository.existsByInviterIdAndInviteeId(inviterId,inviteeId)){
      throw new DuplicateResourceException("이미 초대를 보냈습니다");
    }
    User inviter = userRepository.findById(inviterId)
        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 유저입니다"));
    User invitee = userRepository.findById(inviteeId)
        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 유저 입니다"));
    Couple couple = Couple.builder()
        .inviter(inviter)
        .invitee(invitee)
        .status(Couple.CoupleStatus.PENDING)
        .build();
    return coupleRepository.save(couple);
  }

    // TODO: accept 구현 (@Transactional 필요! - dirty checking으로 save() 없이 반영하려면)
    //   1) coupleRepository.findById(coupleId) → Optional
    //      - 없으면 ResponseStatusException(HttpStatus.NOT_FOUND, "...")
    //   2) 꺼낸 couple의 invitee().getId()가 파라미터로 받은 inviteeId랑 같은지 확인
    //      - 다르면 ResponseStatusException(HttpStatus.FORBIDDEN, "본인에게 온 초대만 수락할 수 있습니다")
    //      - (권한 체크: "이 유저가 이 자원에 대해 이 행동을 할 자격이 있는가"를 확인하는 것)
    //   3) couple.accept() 호출 (엔티티에 만들어둔 메서드)
    //   4) couple 반환 (save() 명시적으로 안 불러도 @Transactional + dirty checking으로 자동 반영됨)
  @Transactional
  @Override
  public Couple accept(Long coupleId,Long inviteeId){
    Couple couple = coupleRepository.findById(coupleId)
        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 초대입니다"));
    if (!couple.getInvitee().getId().equals(inviteeId)){
      throw new ResponseStatusException(HttpStatus.FORBIDDEN,"본인에게 온 초대만 수락 할 수 있습니다");
    }
    couple.accept();
    return couple;
  }
    // TODO: reject 구현 - accept랑 거의 동일, couple.reject() 호출
  @Transactional
  @Override
  public Couple reject(Long coupleId,Long inviteeId){
    Couple couple = coupleRepository.findById(coupleId)
        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 초대입니다"));
    if (!couple.getInvitee().getId().equals(inviteeId)){
      throw new ResponseStatusException(HttpStatus.FORBIDDEN,"본인에게 온 초대만 거절할 수 있습니다");
    }
    couple.reject();
    return couple;
  }
}
