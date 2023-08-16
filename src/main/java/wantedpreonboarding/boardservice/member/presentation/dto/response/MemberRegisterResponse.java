package wantedpreonboarding.boardservice.member.presentation.dto.response;

import lombok.Getter;

@Getter
public class MemberRegisterResponse {

	private Long registeredMemberId;

	public MemberRegisterResponse(Long memberId) {
		this.registeredMemberId = memberId;
	}

}
