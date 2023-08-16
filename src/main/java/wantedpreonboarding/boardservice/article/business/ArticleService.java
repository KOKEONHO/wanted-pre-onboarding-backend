package wantedpreonboarding.boardservice.article.business;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wantedpreonboarding.boardservice.article.domain.Article;
import wantedpreonboarding.boardservice.article.domain.ArticleRepository;
import wantedpreonboarding.boardservice.article.presentation.dto.request.ArticleRequest;
import wantedpreonboarding.boardservice.article.presentation.dto.response.ArticleIdResponse;
import wantedpreonboarding.boardservice.exception.RestApiException;
import wantedpreonboarding.boardservice.exception.code.MemberExceptionCode;
import wantedpreonboarding.boardservice.member.domain.Member;
import wantedpreonboarding.boardservice.member.domain.MemberRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final MemberRepository memberRepository;

	public ArticleIdResponse createArticle(HttpServletRequest httpServletRequest, ArticleRequest request) {
		Long memberId = (Long)httpServletRequest.getAttribute("memberId");
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new RestApiException(MemberExceptionCode.REQUIRED_REGISTER));
		log.info("[ArticleService] [request.title] {}", request.getTitle());
		log.info("[ArticleService] [request.contents] {}", request.getContents());

		Article newArticle = Article.builder()
			.writer(member)
			.title(request.getTitle())
			.contents(request.getContents())
			.build();

		Article saveArticle = articleRepository.save(newArticle);
		return new ArticleIdResponse(saveArticle.getId());
	}
}
