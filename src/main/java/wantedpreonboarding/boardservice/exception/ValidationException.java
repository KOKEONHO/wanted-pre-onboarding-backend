package wantedpreonboarding.boardservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationException {

	private final String field;
	private final String message;

}
