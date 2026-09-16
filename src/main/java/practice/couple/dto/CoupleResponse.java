package practice.couple.dto;

import practice.couple.entity.Couple;
import practice.user2.dto.UserResponse;

import java.time.LocalDateTime;

public record CoupleResponse(Long id, UserResponse inviter, UserResponse invitee, Couple.CoupleStatus status,
                             LocalDateTime requestedAt,LocalDateTime respondedAt) {
  public static CoupleResponse from(Couple couple){
    return new CoupleResponse(
        couple.getId(),
        UserResponse.from(couple.getInviter()),
        UserResponse.from(couple.getInvitee()),
        couple.getStatus(),
        couple.getRequestedAt(),
        couple.getRespondedAt()
    );
  }
}
