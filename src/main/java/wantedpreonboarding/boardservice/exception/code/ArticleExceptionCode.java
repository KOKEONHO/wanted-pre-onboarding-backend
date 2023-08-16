package wantedpreonboarding.boardservice.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wantedpreonboarding.boardservice.common.Code;

@Getter
@RequiredArgsConstructor
public enum ArticleExceptionCode implements Code {

	UNAUTHORIZED_EXCEPTION_DELETE(false, 401, -40001, "다른 사람의 게시글은 삭제할 수 없습니다."),
	UNAUTHORIZED_EXCEPTION_EDIT(false, 401, -40001, "다른 사람의 게시글은 수정할 수 없습니다."),
	NO_EXISTING_ARTICLE(false, 404, -40401, "없는 게시글입니다.");

	private final boolean success;
	private final int status;
	private final int code;
	private final String message;

}
