package wantedpreonboarding.boardservice.article.presentation.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticlesResponse {

	private List<ArticleResponse> articles;
	private PaginationResponse pagination;

	public ArticlesResponse(Page<ArticleResponse> page) {
		this.articles = page.getContent();
		this.pagination = new PaginationResponse(page);
	}
}
