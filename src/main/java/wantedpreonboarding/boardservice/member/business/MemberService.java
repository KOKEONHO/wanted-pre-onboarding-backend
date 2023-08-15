package wantedpreonboarding.boardservice.member.business;

import static wantedpreonboarding.boardservice.exception.code.MemberExceptionCode.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import wantedpreonboarding.boardservice.exception.RestApiException;
import wantedpreonboarding.boardservice.member.domain.Member;
import wantedpreonboarding.boardservice.member.domain.MemberRepository;
import wantedpreonboarding.boardservice.member.presentation.dto.request.MemberRegisterRequest;
import wantedpreonboarding.boardservice.member.presentation.dto.response.MemberRegisterResponse;
import wantedpreonboarding.boardservice.util.PasswordEncoder;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public MemberRegisterResponse register(@Valid @RequestBody MemberRegisterRequest request) {
		memberRepository.findByEmail(request.getEmail())
			.ifPresent(member -> {
				throw new RestApiException(SAME_EMAIL_ALREADY_EXISTS);
			}); // 중복 email로 가입 시도 시에 예외 발생
		String encryptPassword = passwordEncoder.encrypt(request.getEmail(), request.getPassword());
		Member saveMember = memberRepository.save(Member.of(request, encryptPassword));
		return new MemberRegisterResponse(saveMember);
	}

}
