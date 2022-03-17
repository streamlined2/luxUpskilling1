package org.training.upskilling.onlineshop.security.token;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Token {
	
	private final UUID uuid;
	
	public Token() {
		uuid = UUID.randomUUID();
	}
	
}
