package wantedpreonboarding.boardservice.member.presentation.dto.response;

import lombok.Getter;

@Getter
public class MemberLoginResponse {

	private Long memberId;
	private String token;

	private MemberLoginResponse(Long memberId, String token) {
		this.memberId = memberId;
		this.token = token;
	}

	public static MemberLoginResponse of(Long memberId, String token) {
		return new MemberLoginResponse(memberId, token);
	}
}
