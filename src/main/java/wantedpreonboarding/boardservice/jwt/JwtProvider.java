package wantedpreonboarding.boardservice.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private Key key;
	private long expireLength;

	public JwtProvider(@Value("${security.jwt.token.secret}") String secret,
		@Value("${security.jwt.token.expire-length}") long expireLength) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.expireLength = expireLength;
	}

	public Jwt createJwt(Map<String, Object> claims) {
		String accessToken = createToken(claims, getExpireDateAccessToken());
		String refreshToken = createToken(new HashMap<>(), getExpireDateRefreshToken());
		return Jwt.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public String createToken(Map<String, Object> claims, Date expireDate) {
		return Jwts.builder()
			.setClaims(claims)
			.setExpiration(expireDate)
			.signWith(key)
			.compact();
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Date getExpireDateAccessToken() {
		return new Date(System.currentTimeMillis() + expireLength);
	}

	public Date getExpireDateRefreshToken() {
		return new Date(System.currentTimeMillis() + expireLength);
	}
}
