package wantedpreonboarding.boardservice.article;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static wantedpreonboarding.boardservice.restdocsutil.ApiDocumentUtils.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;
import wantedpreonboarding.boardservice.article.business.ArticleService;
import wantedpreonboarding.boardservice.article.domain.Article;
import wantedpreonboarding.boardservice.article.presentation.ArticleController;
import wantedpreonboarding.boardservice.article.presentation.dto.request.ArticleRequest;
import wantedpreonboarding.boardservice.article.presentation.dto.response.ArticleDetailResponse;
import wantedpreonboarding.boardservice.article.presentation.dto.response.ArticleIdResponse;
import wantedpreonboarding.boardservice.article.presentation.dto.response.ArticleResponse;
import wantedpreonboarding.boardservice.article.presentation.dto.response.ArticlesResponse;
import wantedpreonboarding.boardservice.jwt.AuthorizationExtractor;
import wantedpreonboarding.boardservice.jwt.JwtTokenProvider;
import wantedpreonboarding.boardservice.member.domain.Member;
import wantedpreonboarding.boardservice.member.presentation.dto.request.MemberRegisterRequest;
import wantedpreonboarding.boardservice.restdocsutil.RestDocsSetup;

@Slf4j
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest extends RestDocsSetup {

	@MockBean
	private ArticleService articleService;

	@MockBean
	private AuthorizationExtractor authorizationExtractor;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@Test
	@DisplayName("전체 게시글 조회")
	void showArticles() throws Exception {

		List<ArticleResponse> articleResponses = new ArrayList<>();
		for (int i = 1; i < 9; i++) {
			articleResponses.add(new ArticleResponse((long)i, "제목" + i));
		}
		Pageable pageable = PageRequest.of(0, 5);
		Page<ArticleResponse> articles = new PageImpl<>(articleResponses.subList(0, 5), pageable, 5);
		ArticlesResponse articlesResponse = new ArticlesResponse(articles);

		when(articleService.showArticles(any(Pageable.class))).thenReturn(articlesResponse);

		mockMvc.perform(get("/api/articles")
				.param("page", "0")
				.param("size", "5")
				.param("sort", "postedAt,desc")
				.param("sort", "id,desc")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("{class-name}/{method-name}",
				getDocumentRequest(),
				getDocumentResponse(),
				requestParameters(
					parameterWithName("page").description("요청할 페이지 번호 (0부터 시작)"),
					parameterWithName("size").description("한 페이지에 보여줄 게시글 수"),
					parameterWithName("sort").description("정렬 조건 (여러 정렬 조건 가능)")
				),
				responseFields(
					fieldWithPath("success").description("요청 성공 여부"),
					fieldWithPath("status").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("data.articles[]").description("게시글 리스트"),
					fieldWithPath("data.articles[].id").description("게시글 ID"),
					fieldWithPath("data.articles[].title").description("게시글 제목"),
					fieldWithPath("data.pagination.currentPage").description("현재 페이지"),
					fieldWithPath("data.pagination.totalPages").description("전체 페이지 수"),
					fieldWithPath("data.pagination.totalElements").description("전체 게시글 수"),
					fieldWithPath("data.pagination.size").description("한 페이지당 게시글 수")
				)
			));
	}

	@Test
	@DisplayName("특정 게시글 조회")
	void showArticle() throws Exception {

		MemberRegisterRequest registerRequest = new MemberRegisterRequest("고뭉남", "rhrjsgh97@kakao.com", "12345678");
		Article article = new Article(Member.of(registerRequest, "encryptedPassword"), "제목", "내용");
		ArticleDetailResponse articleDetailResponse = ArticleDetailResponse.of(article.getWriter().getEmail(), article);

		when(articleService.findArticleDetailById(any(HttpServletRequest.class), eq(1L))).thenReturn(
			articleDetailResponse);

		mockMvc.perform(get("/api/articles/{articleId}", 1L)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("{class-name}/{method-name}",
				getDocumentRequest(),
				getDocumentResponse(),
				pathParameters(
					parameterWithName("articleId").description("조회할 게시글의 ID")
				),
				responseFields(
					fieldWithPath("success").description("요청 성공 여부"),
					fieldWithPath("status").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("data.writerEmail").description("게시글 작성자 이메일"),
					fieldWithPath("data.title").description("게시글 제목"),
					fieldWithPath("data.contents").description("게시글 내용"),
					fieldWithPath("data.postedAt").description("게시글 작성 시간")
				)
			));

	}

	@Test
	@DisplayName("게시글 생성")
	void createArticle() throws Exception {

		ArticleRequest articleRequest = new ArticleRequest("제목", "내용");
		ArticleIdResponse articleIdResponse = new ArticleIdResponse(1L);

		when(articleService.createArticle(any(HttpServletRequest.class), any(ArticleRequest.class))).thenReturn(
			articleIdResponse);

		String mockToken = "Bearer mock-jwt-token";

		when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);

		mockMvc.perform(post("/api/articles/form")
				.header("Authorization", mockToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(articleRequest))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("{class-name}/{method-name}",
				getDocumentRequest(),
				getDocumentResponse(),
				requestFields(
					fieldWithPath("title").description("게시글 제목"),
					fieldWithPath("contents").description("게시글 내용")
				),
				responseFields(
					fieldWithPath("success").description("요청 성공 여부"),
					fieldWithPath("status").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("data.id").description("새로 작성된 게시글의 고유 번호")
				)
			));
	}

	@Test
	@DisplayName("게시글 수정")
	void updateArticle() throws Exception {

		ArticleRequest articleRequest = new ArticleRequest("바뀐 제목", "바뀐 내용");
		ArticleIdResponse articleIdResponse = new ArticleIdResponse(1L);

		when(articleService.updateArticle(any(HttpServletRequest.class), any(ArticleRequest.class), eq(1L))).thenReturn(
			articleIdResponse);

		mockMvc.perform(put("/api/articles/{articleId}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(articleRequest))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("{class-name}/{method-name}",
				getDocumentRequest(),
				getDocumentResponse(),
				pathParameters(
					parameterWithName("articleId").description("수정할 게시글의 ID")
				),
				requestFields(
					fieldWithPath("title").description("수정하려는 게시글 제목"),
					fieldWithPath("contents").description("수정하려는 게시글 내용")
				),
				responseFields(
					fieldWithPath("success").description("요청 성공 여부"),
					fieldWithPath("status").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("data.id").description("수정된 게시글의 고유 번호")
				)
			));

	}

	@Test
	@DisplayName("게시글 삭제")
	void deleteArticle() throws Exception {

		ArticleIdResponse articleIdResponse = new ArticleIdResponse(1L);

		when(articleService.deleteArticle(any(HttpServletRequest.class), eq(1L))).thenReturn(
			articleIdResponse);

		mockMvc.perform(delete("/api/articles/{articleId}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("{class-name}/{method-name}",
				getDocumentRequest(),
				getDocumentResponse(),
				pathParameters(
					parameterWithName("articleId").description("삭제할 게시글의 ID")
				),
				responseFields(
					fieldWithPath("success").description("요청 성공 여부"),
					fieldWithPath("status").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("data.id").description("삭제된 게시글의 고유 번호")
				)
			));

	}
}