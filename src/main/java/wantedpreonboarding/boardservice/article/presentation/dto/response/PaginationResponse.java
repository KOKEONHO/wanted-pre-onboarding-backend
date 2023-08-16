package wantedpreonboarding.boardservice.article.presentation.dto.response;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaginationResponse {

	private int currentPage;
	private int totalPages;
	private long totalElements;
	private int size;

	public PaginationResponse(Page<ArticleResponse> page) {
		this.currentPage = page.getNumber() + 1;
		this.totalPages = page.getTotalPages();
		this.totalElements = page.getTotalElements();
		this.size = page.getSize();
	}
}
