package wantedpreonboarding.boardservice.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wantedpreonboarding.boardservice.common.Code;

@Getter
@RequiredArgsConstructor
public enum TokenExceptionCode implements Code {

	TOKEN_INVALID(false, 401, -40100, "유효하지 않은 토큰입니다."),
	TOKEN_EXPIRED(false, 401, -40101, "만료된 토큰입니다."),
	TOKEN_IS_NULL(false, 401, -40102, "토큰이 없습니다."),
	TOKEN_CAN_NOT_DECODE(false, 401, -40103, "올바르지 않은 토큰 형식입니다.");

	private final boolean success;
	private final int status;
	private final int code;
	private final String message;

}
