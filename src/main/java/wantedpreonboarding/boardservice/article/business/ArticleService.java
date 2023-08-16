package wantedpreonboarding.boardservice.article.business;

import static wantedpreonboarding.boardservice.exception.code.ArticleExceptionCode.*;
import static wantedpreonboarding.boardservice.exception.code.MemberExceptionCode.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wantedpreonboarding.boardservice.article.domain.Article;
import wantedpreonboarding.boardservice.article.domain.ArticleRepository;
import wantedpreonboarding.boardservice.article.presentation.dto.request.ArticleRequest;
import wantedpreonboarding.boardservice.article.presentation.dto.response.ArticleDetailResponse;
import wantedpreonboarding.boardservice.article.presentation.dto.response.ArticleIdResponse;
import wantedpreonboarding.boardservice.article.presentation.dto.response.ArticleResponse;
import wantedpreonboarding.boardservice.article.presentation.dto.response.ArticlesResponse;
import wantedpreonboarding.boardservice.exception.RestApiException;
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
			.orElseThrow(() -> new RestApiException(REQUIRED_REGISTER));
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

	public ArticleIdResponse updateArticle(HttpServletRequest httpServletRequest, ArticleRequest request,
		Long articleId) {
		Long memberId = (Long)httpServletRequest.getAttribute("memberId");
		if (memberId == null) {
			throw new RestApiException(REQUIRED_REGISTER);
		}
		Article foundArticle = articleRepository.findById(articleId)
			.orElseThrow(() -> new RestApiException(NO_EXISTING_ARTICLE));
		if (foundArticle.getWriter().getId() != memberId) {
			throw new RestApiException(UNAUTHORIZED_EXCEPTION_EDIT);
		}
		foundArticle.updateArticle(request.getTitle(), request.getContents());
		return new ArticleIdResponse(foundArticle.getId());
	}

	public ArticleIdResponse deleteArticle(HttpServletRequest httpServletRequest, Long articleId) {
		Long memberId = (Long)httpServletRequest.getAttribute("memberId");
		if (memberId == null) {
			throw new RestApiException(REQUIRED_REGISTER);
		}
		Article foundArticle = articleRepository.findById(articleId)
			.orElseThrow(() -> new RestApiException(NO_EXISTING_ARTICLE));
		if (foundArticle.getWriter().getId() != memberId) {
			throw new RestApiException(UNAUTHORIZED_EXCEPTION_DELETE);
		}
		articleRepository.delete(foundArticle);
		return new ArticleIdResponse(foundArticle.getId());
	}

	public ArticlesResponse showArticles(Pageable pageable) {
		Page<Article> page = articleRepository.findAll(pageable);
		Page<ArticleResponse> articles = page.map(article -> new ArticleResponse(article.getId(), article.getTitle()));
		return new ArticlesResponse(articles);
	}

	public ArticleDetailResponse findArticleDetailById(HttpServletRequest httpServletRequest, Long articleId) {
		Long memberId = (Long)httpServletRequest.getAttribute("memberId");
		if (memberId == null) {
			throw new RestApiException(REQUIRED_REGISTER);
		}
		Article foundArticle = findArticle(articleId);
		String writerEmail = foundArticle.getWriter().getEmail();
		return ArticleDetailResponse.of(writerEmail, foundArticle);
	}

	private Article findArticle(Long articleId) {
		return articleRepository.findById(articleId)
			.orElseThrow(() -> new RestApiException(NO_EXISTING_ARTICLE));
	}
}
