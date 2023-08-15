package wantedpreonboarding.boardservice.member.presentation.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRegisterRequest {

	@NotBlank
	@Pattern(message = "이름은 한글만 입력 가능합니다.",
		regexp = "^[가-힣]+$")
	private String name;

	@NotBlank
	@Email(message = "잘못된 이메일 형식입니다.")
	private String email;

	@NotBlank
	@Pattern(message = "비밀번호는 공백을 포함하지 않는 8~16자이며, 최소 하나의 특수문자를 포함해야 합니다.",
		regexp = "(?=\\S+$).{8,16}")
	private String password;

	public MemberRegisterRequest(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
