package wantedpreonboarding.boardservice.jwt;

import static wantedpreonboarding.boardservice.exception.code.TokenExceptionCode.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;
import wantedpreonboarding.boardservice.exception.RestApiException;

@Slf4j
@Component
public class BearerAuthInterceptor implements HandlerInterceptor {

	private AuthorizationExtractor authExtractor;
	private JwtTokenProvider jwtTokenProvider;

	public BearerAuthInterceptor(AuthorizationExtractor authExtractor, JwtTokenProvider jwtTokenProvider) {
		this.authExtractor = authExtractor;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		log.info("[BearerAuthInterceptor.preHandle 호출]");
		String token = authExtractor.extract(request, "Bearer");
		if (StringUtils.isEmpty(token)) {
			return true;
		}

		if (!jwtTokenProvider.validateToken(token)) {
			throw new RestApiException(TOKEN_INVALID);
		}

		Long memberId = jwtTokenProvider.getMemberId(token);
		request.setAttribute("memberId", memberId);
		return true;
	}
}
