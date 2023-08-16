package wantedpreonboarding.boardservice.article.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wantedpreonboarding.boardservice.member.domain.Member;

@Entity
@Getter
@Table(name = "article")
@NoArgsConstructor
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member writer;

	@Column(name = "title")
	private String title;

	@Column(name = "contents")
	private String contents;

	@Column(name = "posted_at")
	private LocalDateTime postedAt;

	@Builder
	public Article(Member writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.postedAt = LocalDateTime.now();
	}
}
