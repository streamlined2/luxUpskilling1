package org.training.upskilling.onlineshop.security.token;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Token {
	
	private static final SecureRandom random = new SecureRandom();
	private static final int TOKEN_VALUE_SIZE = 300;
	
	private final byte[] value;
	private final LocalDateTime expirationTime;
	
	public Token(byte[] value, long lifeTime) {
		this.value = value;
		expirationTime = LocalDateTime.now().plusSeconds(lifeTime);
	}
	
	public Token(long lifeTime) {
		value = new byte[TOKEN_VALUE_SIZE];
		random.nextBytes(value);
		expirationTime = LocalDateTime.now().plusSeconds(lifeTime);
	}
	
}
