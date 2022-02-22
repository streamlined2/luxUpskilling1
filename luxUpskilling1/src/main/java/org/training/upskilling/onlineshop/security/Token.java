package org.training.upskilling.onlineshop.security;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class Token {
	
	private static final SecureRandom random = new SecureRandom();
	private static final int TOKEN_SIZE = 100;
	private static final long TOKEN_LIFE_TIME = 60;
	
	private final byte[] value;
	private final LocalDateTime expirationTime;
	
	private Token(byte[] value) {
		this.value = value;
		expirationTime = LocalDateTime.now().plusSeconds(TOKEN_LIFE_TIME);
	}
	
	public boolean isValid() {
		return expirationTime.isAfter(LocalDateTime.now());
	}
	
	public static Token getToken() {
		byte[] bytes = new byte[TOKEN_SIZE];
		random.nextBytes(bytes);
		return new Token(bytes);
	}
	
}
