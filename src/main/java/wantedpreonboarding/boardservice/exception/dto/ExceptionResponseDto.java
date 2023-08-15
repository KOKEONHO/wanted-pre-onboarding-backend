package wantedpreonboarding.boardservice.exception.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wantedpreonboarding.boardservice.exception.ValidationException;

@Getter
@Builder
@RequiredArgsConstructor
public class ExceptionResponseDto {

	private final boolean success;
	private final int status;
	private final int code;
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<ValidationException> exceptions;
}
