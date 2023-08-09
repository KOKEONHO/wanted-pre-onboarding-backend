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
		String rdsUrl = System.getProperty("rds.url");
		String rdsUsername = System.getProperty("rds.username");
		String rdsPassword = System.getProperty("rds.password");

		String encryptLocalDbUrl = jasyptEncrypt(localDbUrl);
		String encryptLocalDbUsername = jasyptEncrypt(localDbUsername);
		String encryptLocalDbPassword = jasyptEncrypt(localDbPassword);
		String encryptRdsUrl = jasyptEncrypt(rdsUrl);
		String encryptRdsUsername = jasyptEncrypt(rdsUsername);
		String encryptRdsPassword = jasyptEncrypt(rdsPassword);

		log.info("Encrypted localDbUrl: {}", encryptLocalDbUrl);
		log.info("Encrypted localDbUsername: {}", encryptLocalDbUsername);
		log.info("Encrypted localDbPassword: {}", encryptLocalDbPassword);
		log.info("Encrypted rdsUrl: {}", encryptRdsUrl);
		log.info("Encrypted rdsUsername: {}", encryptRdsUsername);
		log.info("Encrypted rdsPassword: {}", encryptRdsPassword);

		assertThat(localDbUrl).isEqualTo(jasyptDecrypt(encryptLocalDbUrl));
		assertThat(localDbUsername).isEqualTo(jasyptDecrypt(encryptLocalDbUsername));
		assertThat(localDbPassword).isEqualTo(jasyptDecrypt(encryptLocalDbPassword));
		assertThat(rdsUrl).isEqualTo(jasyptDecrypt(encryptRdsUrl));
		assertThat(rdsUsername).isEqualTo(jasyptDecrypt(encryptRdsUsername));
		assertThat(rdsPassword).isEqualTo(jasyptDecrypt(encryptRdsPassword));
	}

	private String jasyptEncrypt(String input) {
		String key = System.getProperty("jasypt.encryptor.password");
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setAlgorithm("PBEWithMD5AndDES");
		encryptor.setPassword(key);
		return encryptor.encrypt(input);
	}

	private String jasyptDecrypt(String input) {
		String key = System.getProperty("jasypt.encryptor.password");
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setAlgorithm("PBEWithMD5AndDES");
		encryptor.setPassword(key);
		return encryptor.decrypt(input);
	}
}
