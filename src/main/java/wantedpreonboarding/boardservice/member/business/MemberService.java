package wantedpreonboarding.boardservice.member.business;

import static wantedpreonboarding.boardservice.exception.code.MemberExceptionCode.*;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wantedpreonboarding.boardservice.exception.RestApiException;
import wantedpreonboarding.boardservice.jwt.JwtTokenProvider;
import wantedpreonboarding.boardservice.member.domain.Member;
import wantedpreonboarding.boardservice.member.domain.MemberRepository;
import wantedpreonboarding.boardservice.member.presentation.dto.MemberIdEmailDto;
import wantedpreonboarding.boardservice.member.presentation.dto.request.MemberLoginRequest;
import wantedpreonboarding.boardservice.member.presentation.dto.request.MemberRegisterRequest;
import wantedpreonboarding.boardservice.member.presentation.dto.response.MemberLoginResponse;
import wantedpreonboarding.boardservice.member.presentation.dto.response.MemberRegisterResponse;
import wantedpreonboarding.boardservice.util.PasswordEncoder;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	public MemberRegisterResponse register(MemberRegisterRequest request) {
		memberRepository.findByEmail(request.getEmail())
			.ifPresent(member -> {
				throw new RestApiException(SAME_EMAIL_ALREADY_EXISTS);
			}); // 중복 email로 가입 시도 시에 예외 발생
		String encryptPassword = passwordEncoder.encrypt(request.getEmail(), request.getPassword());
		Member saveMember = memberRepository.save(Member.of(request, encryptPassword));
		return new MemberRegisterResponse(saveMember);
	}

	public MemberLoginResponse login(MemberLoginRequest request) {
		log.info("[MemberService] [login]");
		Member member = memberRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new RestApiException(REQUIRED_REGISTER));
		if (!member.getPassword().equals(passwordEncoder.encrypt(request.getEmail(), request.getPassword()))) {
			throw new RestApiException(WRONG_PASSWORD);
		}
		String token = jwtTokenProvider.createToken(MemberIdEmailDto.from(member));
		log.info("[MemberService] [token] {}", token);
		return MemberLoginResponse.of(member.getId(), token);
	}
}
