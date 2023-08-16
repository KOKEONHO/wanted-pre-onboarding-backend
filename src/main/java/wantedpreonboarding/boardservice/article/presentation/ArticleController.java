package wantedpreonboarding.boardservice.article.presentation;

import static wantedpreonboarding.boardservice.response.code.success.SuccessResponseCode.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wantedpreonboarding.boardservice.article.business.ArticleService;
import wantedpreonboarding.boardservice.article.presentation.dto.request.ArticleRequest;
import wantedpreonboarding.boardservice.article.presentation.dto.response.ArticleIdResponse;
import wantedpreonboarding.boardservice.response.dto.ResponseDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

	private final ArticleService articleService;

	@PostMapping("/form")
	public ResponseDto<ArticleIdResponse> createArticle(HttpServletRequest httpServletRequest,
		@RequestBody ArticleRequest request) {
		ArticleIdResponse articleIdResponse = articleService.createArticle(httpServletRequest, request);
		return ResponseDto.of(RESPONSE_SUCCESS, articleIdResponse);
	}
}
