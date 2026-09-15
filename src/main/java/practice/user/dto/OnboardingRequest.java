package practice.user.dto;

public record OnboardingRequest(Double energy,Double vibe,Double depth) {
}
// TODO: record로 OnboardingRequest 만들기
//   필드 3개: energy(Double), vibe(Double), depth(Double)
//   (userId는 body가 아니라 URL 경로(@PathVariable)로 받을 거라 여기 안 넣어요)
