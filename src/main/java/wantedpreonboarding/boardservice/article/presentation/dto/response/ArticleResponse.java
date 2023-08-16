package wantedpreonboarding.boardservice.article.presentation.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleResponse {

	private Long id;
	private String title;

	public ArticleResponse(Long id, String title) {
		this.id = id;
		this.title = title;
	}
}
