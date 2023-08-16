package wantedpreonboarding.boardservice.exception.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import wantedpreonboarding.boardservice.common.Code;
import wantedpreonboarding.boardservice.exception.RestApiException;
import wantedpreonboarding.boardservice.exception.ValidationException;
import wantedpreonboarding.boardservice.exception.code.CommonExceptionCode;
import wantedpreonboarding.boardservice.exception.dto.ExceptionResponseDto;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RestApiException.class)
	public ResponseEntity<Object> handleCustomException(RestApiException e) {
		log.warn("handleCustomException", e);
		Code errorCode = e.getErrorCode();
		return handleExceptionInternal(errorCode);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
		log.warn("handleIllegalArgumentException", e);
		Code errorCode = CommonExceptionCode.INVALID_PARAMETER;
		return handleExceptionInternal(errorCode, e.getMessage());
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleAllException(Exception exception) {
		log.warn("Exception", exception);
		Code errorCode = CommonExceptionCode.INTERNAL_SERVER_ERROR;
		return handleExceptionInternal(errorCode);
	}

	private ResponseEntity<Object> handleExceptionInternal(Code errorCode) {
		return ResponseEntity.status(errorCode.getStatus())
			.body(makeExceptionResponse(errorCode));
	}

	private ResponseEntity<Object> handleExceptionInternal(Code errorCode, String message) {
		return ResponseEntity.status(errorCode.getStatus())
			.body(makeExceptionResponse(errorCode, message));
	}

	private ResponseEntity<Object> handleExceptionInternal(BindException e, Code errorCode) {
		return ResponseEntity.status(errorCode.getStatus())
			.body(makeExceptionResponse(e, errorCode));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException e,
		HttpHeaders headers,
		HttpStatus status,
		WebRequest request) {
		log.warn("handleMethodArgumentNotValid", e);
		Code errorCode = CommonExceptionCode.INVALID_PARAMETER;
		return handleExceptionInternal(e, errorCode);
	}

	private ExceptionResponseDto makeExceptionResponse(Code errorCode, String message) {
		return ExceptionResponseDto.builder()
			.success(errorCode.isSuccess())
			.status(errorCode.getStatus())
			.code(errorCode.getCode())
			.message(message)
			.build();
	}

	private ExceptionResponseDto makeExceptionResponse(Code errorCode) {
		return ExceptionResponseDto.builder()
			.success(errorCode.isSuccess())
			.status(errorCode.getStatus())
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.build();
	}

	private ExceptionResponseDto makeExceptionResponse(BindException e, Code errorCode) {
		List<ValidationException> validationExceptionList = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(fieldError -> new ValidationException(fieldError.getField(), fieldError.getDefaultMessage()))
			.collect(Collectors.toList());

		return ExceptionResponseDto.builder()
			.success(errorCode.isSuccess())
			.status(errorCode.getStatus())
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.exceptions(validationExceptionList)
			.build();
	}
}
