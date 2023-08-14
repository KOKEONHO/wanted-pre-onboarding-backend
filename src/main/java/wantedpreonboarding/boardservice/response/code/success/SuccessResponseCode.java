package wantedpreonboarding.boardservice.response.code.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wantedpreonboarding.boardservice.response.code.Code;

@Getter
@RequiredArgsConstructor
public enum SuccessResponseCode implements Code {

	RESPONSE_SUCCESS(true, 200, 20000, "요청이 완료되었습니다.");

	private final boolean success;
	private final int status;
	private final int code;
	private final String message;

}
