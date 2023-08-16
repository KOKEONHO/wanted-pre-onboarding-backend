package wantedpreonboarding.boardservice.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.ToString;
import wantedpreonboarding.boardservice.common.Code;

@Getter
@ToString
public class ResponseDto<T> {

	private final boolean success;
	private final int status;
	private final int code;
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private T data;

	private ResponseDto(Code code, T data) {
		this.success = code.isSuccess();
		this.status = code.getStatus();
		this.code = code.getCode();
		this.message = code.getMessage();
		this.data = data;
	}

	public static <T> ResponseDto<T> of(Code code, T data) {
		return new ResponseDto<>(code, data);
	}
}
