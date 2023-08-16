package wantedpreonboarding.boardservice.member.presentation.dto;

import lombok.Getter;
import wantedpreonboarding.boardservice.member.domain.Member;

@Getter
public class MemberIdEmailDto {

	private Long memberId;
	private String email;

	private MemberIdEmailDto(Long memberId, String email) {
		this.memberId = memberId;
		this.email = email;
	}

	public static MemberIdEmailDto from(Member member) {
		return new MemberIdEmailDto(member.getId(), member.getEmail());
	}
}
