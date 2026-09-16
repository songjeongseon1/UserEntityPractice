package practice.couple.service;

// TODO: import practice.couple.entity.Couple

import practice.couple.entity.Couple;

public interface CoupleService {

    // TODO: invite 시그니처 - inviterId, inviteeId(둘 다 Long) 받아서 Couple 반환
  Couple invite(Long inviterId,Long inviteeId);
    // TODO: accept 시그니처 - coupleId, inviteeId(둘 다 Long) 받아서 Couple 반환
    //   (inviteeId는 "정말 이 커플의 초대받은 사람이 맞는지" 검증용으로 필요)
  Couple accept(Long coupleId,Long inviteeId);
    // TODO: reject 시그니처 - accept랑 동일한 파라미터, Couple 반환
  Couple reject(Long coupleId,Long inviteeId);
}
