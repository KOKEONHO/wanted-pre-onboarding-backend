package wantedpreonboarding.boardservice.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

	public String encrypt(String email, String password) {

		try {
			KeySpec spec = new PBEKeySpec(password.toCharArray(), getSalt(email), 85319, 128);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			byte[] hash = factory.generateSecret(spec).getEncoded();
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeySpecException exception) {
			throw new RuntimeException(exception);
		}

	}

	private byte[] getSalt(String email)
		throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		byte[] keyBytes = email.getBytes("UTF-8");

		return digest.digest(keyBytes);
	}

}