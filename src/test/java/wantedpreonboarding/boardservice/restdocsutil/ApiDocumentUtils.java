package wantedpreonboarding.boardservice.restdocsutil;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

public interface ApiDocumentUtils {

	static OperationRequestPreprocessor getDocumentRequest() {
		return preprocessRequest(
			modifyUris()
				.scheme("https")
				.host("gomungnamcafe.site")
				.removePort(),
			prettyPrint()
		);
	}

	static OperationResponsePreprocessor getDocumentResponse() {
		return preprocessResponse(prettyPrint());
	}

}
