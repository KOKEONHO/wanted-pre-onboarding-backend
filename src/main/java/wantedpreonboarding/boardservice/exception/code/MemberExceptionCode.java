package wantedpreonboarding.boardservice.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wantedpreonboarding.boardservice.common.Code;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionCode implements Code {

	AUTHENTICATION_INVALID_USER(false, 401, -40104, "이용할 수 없는 사용자입니다."),
	REQUIRED_REGISTER(false, 403, -40301, "회원가입이 필요한 사용자입니다."),
	WRONG_PASSWORD(false, 401, -40101, "비밀번호가 틀렸습니다."),
	SAME_ID_ALREADY_EXISTS(false, 400, -40000, "중복된 ID입니다.");

	private final boolean success;
	private final int status;
	private final int code;
	private final String message;

}