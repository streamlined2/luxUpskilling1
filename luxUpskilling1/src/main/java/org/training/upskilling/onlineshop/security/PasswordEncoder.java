package org.training.upskilling.onlineshop.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordEncoder {

	private static final String SHA_256 = "SHA-256";
	private final MessageDigest digester;

	public PasswordEncoder() {
		try {
			digester = MessageDigest.getInstance(SHA_256);
		} catch (NoSuchAlgorithmException e) {
			log.error("no algorithm {} provider found", SHA_256);
			throw new MissingAlgorithmException(String.format("no algorithm %s provider found", SHA_256), e);
		}
	}

	public String encode(String password, String salt) {
		return Base64.getEncoder().encodeToString(digester.digest((password + salt).getBytes()));
	}

	public boolean matches(String encodedPassword, String password, String salt) {
		return encodedPassword.equals(encode(password, salt));
	}

}
