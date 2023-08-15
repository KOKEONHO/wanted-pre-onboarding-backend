package wantedpreonboarding.boardservice.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wantedpreonboarding.boardservice.common.Code;

@Getter
@RequiredArgsConstructor
public enum CommonExceptionCode implements Code {

	INVALID_PARAMETER(false, 400, -40000, "잘못된 매개변수가 포함되었습니다."),
	RESOURCE_NOT_FOUND(false, 404, -40400, "리소스가 존재하지 않습니다."),
	INTERNAL_SERVER_ERROR(false, 500, -50000, "내부 서버 오류입니다.");

	private final boolean success;
	private final int status;
	private final int code;
	private final String message;

}