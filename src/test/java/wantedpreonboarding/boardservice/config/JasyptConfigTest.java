package wantedpreonboarding.boardservice.config;

import static org.assertj.core.api.Assertions.*;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JasyptConfigTest {

	@Test
	void jasypt() {
		String localDbUrl = System.getProperty("localdb.url");
		String localDbUsername = System.getProperty("localdb.username");
		String localDbPassword = System.getProperty("localdb.password");

		String encryptLocalDbUrl = jasyptEncrypt(localDbUrl);
		String encryptLocalDbUsername = jasyptEncrypt(localDbUsername);
		String encryptLocalDbPassword = jasyptEncrypt(localDbPassword);

		log.info("Encrypted localDbUrl: {}", encryptLocalDbUrl);
		log.info("Encrypted localDbUsername: {}", encryptLocalDbUsername);
		log.info("Encrypted localDbPwssword: {}", encryptLocalDbPassword);

		assertThat(localDbUrl).isEqualTo(jasyptDecrypt(encryptLocalDbUrl));
		assertThat(localDbUsername).isEqualTo(jasyptDecrypt(encryptLocalDbUsername));
		assertThat(localDbPassword).isEqualTo(jasyptDecrypt(encryptLocalDbPassword));
	}

	private String jasyptEncrypt(String input) {
		String key = System.getProperty("password");
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setAlgorithm("PBEWithMD5AndDES");
		encryptor.setPassword(key);
		return encryptor.encrypt(input);
	}

	private String jasyptDecrypt(String input) {
		String key = System.getProperty("password");
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setAlgorithm("PBEWithMD5AndDES");
		encryptor.setPassword(key);
		return encryptor.decrypt(input);
	}
}
