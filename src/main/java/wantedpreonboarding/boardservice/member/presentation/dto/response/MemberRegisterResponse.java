package wantedpreonboarding.boardservice.member.presentation.dto.response;

import lombok.Getter;
import wantedpreonboarding.boardservice.member.domain.Member;

@Getter
public class MemberRegisterResponse {

	private Long registeredMemberId;

	public MemberRegisterResponse(Member member) {
		this.registeredMemberId = member.getId();
	}

}
