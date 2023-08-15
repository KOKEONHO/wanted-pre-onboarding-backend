package wantedpreonboarding.boardservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wantedpreonboarding.boardservice.common.Code;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

	private final Code errorCode;

	@Override
	public String getMessage() {
		return errorCode.getMessage();
	}
}
