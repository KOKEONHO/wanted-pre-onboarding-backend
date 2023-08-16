package wantedpreonboarding.boardservice.member;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static wantedpreonboarding.boardservice.restdocsutil.ApiDocumentUtils.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;
import wantedpreonboarding.boardservice.jwt.AuthorizationExtractor;
import wantedpreonboarding.boardservice.jwt.JwtTokenProvider;
import wantedpreonboarding.boardservice.member.business.MemberService;
import wantedpreonboarding.boardservice.member.presentation.MemberController;
import wantedpreonboarding.boardservice.member.presentation.dto.request.MemberLoginRequest;
import wantedpreonboarding.boardservice.member.presentation.dto.request.MemberRegisterRequest;
import wantedpreonboarding.boardservice.member.presentation.dto.response.MemberLoginResponse;
import wantedpreonboarding.boardservice.member.presentation.dto.response.MemberRegisterResponse;
import wantedpreonboarding.boardservice.restdocsutil.RestDocsSetup;

@Slf4j
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest extends RestDocsSetup {

	@MockBean
	private MemberService memberService;

	@MockBean
	private AuthorizationExtractor authorizationExtractor;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@Test
	@DisplayName("신규 사용자 회원가입")
	void register() throws Exception {

		MemberRegisterRequest registerRequest = new MemberRegisterRequest("고뭉남", "rhrjsgh97@kakao.com", "12345678");
		MemberRegisterResponse memberRegisterResponse = new MemberRegisterResponse(1L);

		when(memberService.register(any(MemberRegisterRequest.class))).thenReturn(memberRegisterResponse);

		mockMvc.perform(post("/api/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerRequest))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("{class-name}/{method-name}",
				getDocumentRequest(),
				getDocumentResponse(),
				requestFields(
					fieldWithPath("name").description("신규 사용자 이름"),
					fieldWithPath("email").description("신규 사용자 이메일"),
					fieldWithPath("password").description("신규 사용자 비밀번호")
				),
				responseFields(
					fieldWithPath("success").description("요청 성공 여부"),
					fieldWithPath("status").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("data.registeredMemberId").description("회원가입 후 발급되는 고유 번호")
				)
			));

	}

	@Test
	@DisplayName("사용자 로그인")
	void login() throws Exception {

		MemberLoginRequest loginRequest = new MemberLoginRequest("rhrjsgh97@kakao.com", "12345678");
		MemberLoginResponse loginResponse = MemberLoginResponse.of(1L, "accesstokennotrefreshtoken");

		when(memberService.login(any(MemberLoginRequest.class))).thenReturn(loginResponse);

		mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("{class-name}/{method-name}",
				getDocumentRequest(),
				getDocumentResponse(),
				requestFields(
					fieldWithPath("email").description("이메일"),
					fieldWithPath("password").description("비밀번호")
				),
				responseFields(
					fieldWithPath("success").description("요청 성공 여부"),
					fieldWithPath("status").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("data.memberId").description("사용자 고유 번호"),
					fieldWithPath("data.token").description("액세스 토큰")
				)
			));
	}

}
