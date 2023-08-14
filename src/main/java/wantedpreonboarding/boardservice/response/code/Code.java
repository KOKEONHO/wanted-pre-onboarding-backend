package wantedpreonboarding.boardservice.response.code;

public interface Code {

	boolean isSuccess();

	int getStatus();

	int getCode();

	String getMessage();

}
