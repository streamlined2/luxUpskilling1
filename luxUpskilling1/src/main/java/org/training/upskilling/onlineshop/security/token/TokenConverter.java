package org.training.upskilling.onlineshop.security.token;

import java.util.UUID;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenConverter {

	public String toString(Token token) {
		return token.getUuid().toString();
	}

	public Token parse(String value) {
		return new Token(UUID.fromString(value));
	}

}
