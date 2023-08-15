package wantedpreonboarding.boardservice.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import wantedpreonboarding.boardservice.member.presentation.dto.MemberIdEmailDto;

@Slf4j
@Component
public class JwtTokenProvider {

	private String secretKey;
	private long expireLength;

	public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
		@Value("${security.jwt.token.expire-length}") long expireLength) {
		this.secretKey = secretKey;
		this.expireLength = expireLength;
	}

	public String createToken(MemberIdEmailDto subject) {

		Date now = new Date();
		Date validity = new Date(now.getTime() + expireLength);
		return Jwts.builder()
			.claim("memberId", subject.getMemberId())
			.claim("email", subject.getEmail())
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			log.info("[JwtTokenProvider]");
			log.info("[claims = {}]", claims);
			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;
		} catch (JwtException exception) {
			return false;
		} catch (IllegalArgumentException exception) {
			return false;
		}
	}
}
