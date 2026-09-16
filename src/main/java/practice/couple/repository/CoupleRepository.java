package practice.couple.repository;

// TODO: import 채우기
//   - org.springframework.data.jpa.repository.JpaRepository
//   - practice.couple.entity.Couple
//   - java.util.List, java.util.Optional

import org.springframework.data.jpa.repository.JpaRepository;
import practice.couple.entity.Couple;

import java.util.List;
import java.util.Optional;

public interface CoupleRepository extends JpaRepository<Couple, Long> {

    // TODO 1: 이 유저가 inviter든 invitee든 상관없이, 속해 있는 커플 관계 찾기
    //   메서드명 힌트: findBy + Inviter(연관관계 필드명) + Id + Or + Invitee(연관관계 필드명) + Id
    //   반환 타입: Optional<Couple>
    //   파라미터: Long inviterId, Long inviteeId
    Optional<Couple> findByInviterIdOrInviteeId(Long inviterId,Long inviteeId);

    List<Couple> findByInviteeIdAndStatus(Long inviteeId, Couple.CoupleStatus status);
    // TODO 3: 같은 두 사람 사이에 이미 초대가 있는지 중복 체크
    //   메서드명 힌트: existsBy + Inviter + Id + And + Invitee + Id
    //   반환 타입: boolean
    //   파라미터: Long inviterId, Long inviteeId
    boolean existsByInviterIdAndInviteeId(Long inviterId,Long inviteeId);
}
