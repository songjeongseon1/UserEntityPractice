package practice.user.service;

// TODO: import practice.user.entity.User (혹은 Long userId만 받을 거면 필요 없을 수도 있음)

import practice.user.entity.UserStyle;

public interface UserStyleService {
  UserStyle saveOnboarding(Long userId,Double energy,Double vibe,Double depth);
    // TODO: saveOnboarding 메서드 시그니처 선언하세요.
    //   파라미터: userId(Long), energy(Double), vibe(Double), depth(Double)
    //   반환 타입: void (저장만 하고 따로 돌려줄 게 없음)

}
