package wantedpreonboarding.boardservice.article.presentation.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wantedpreonboarding.boardservice.article.domain.Article;

@Getter
@NoArgsConstructor
public class ArticleDetailResponse {

	private String writerEmail;
	private String title;
	private String contents;
	private LocalDateTime postedAt;

	private ArticleDetailResponse(String writerEmail, String title, String contents, LocalDateTime postedAt) {
		this.writerEmail = writerEmail;
		this.title = title;
		this.contents = contents;
		this.postedAt = postedAt;
	}

	public static ArticleDetailResponse of(String writerEmail, Article article) {
		return new ArticleDetailResponse(
			writerEmail,
			article.getTitle(),
			article.getContents(),
			article.getPostedAt()
		);
	}
}
