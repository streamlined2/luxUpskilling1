package org.training.upskilling.onlineshop.security.token;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Token {
	
	private final UUID uuid;
	private final LocalDateTime expirationTime;
	
	public Token(long lifeTime) {
		uuid = UUID.randomUUID();
		expirationTime = LocalDateTime.now().plusSeconds(lifeTime);
	}
	
}
