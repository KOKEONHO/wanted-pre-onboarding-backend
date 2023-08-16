package wantedpreonboarding.boardservice.member.presentation;

import static wantedpreonboarding.boardservice.response.code.success.SuccessResponseCode.*;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wantedpreonboarding.boardservice.member.business.MemberService;
import wantedpreonboarding.boardservice.member.presentation.dto.request.MemberLoginRequest;
import wantedpreonboarding.boardservice.member.presentation.dto.request.MemberRegisterRequest;
import wantedpreonboarding.boardservice.member.presentation.dto.response.MemberLoginResponse;
import wantedpreonboarding.boardservice.member.presentation.dto.response.MemberRegisterResponse;
import wantedpreonboarding.boardservice.response.dto.ResponseDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/register")
	public ResponseDto<MemberRegisterResponse> register(@Valid @RequestBody MemberRegisterRequest request) {
		MemberRegisterResponse memberRegisterResponse = memberService.register(request);
		return ResponseDto.of(RESPONSE_SUCCESS, memberRegisterResponse);
	}

	@PostMapping("/login")
	public ResponseDto<MemberLoginResponse> login(@Valid @RequestBody MemberLoginRequest request) {
		MemberLoginResponse memberLoginResponse = memberService.login(request);
		return ResponseDto.of(RESPONSE_SUCCESS, memberLoginResponse);
	}
}
