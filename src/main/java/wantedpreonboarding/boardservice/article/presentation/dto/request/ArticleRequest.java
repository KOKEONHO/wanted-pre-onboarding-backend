package wantedpreonboarding.boardservice.article.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleRequest {

	private String title;
	private String contents;

	public ArticleRequest(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
}
