package org.training.upskilling.onlineshop.security;

import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class TokenConverter {
	
	public String toString(Token token) {
		return new StringBuilder()
				.append(DateTimeFormatter.ISO_DATE_TIME.format(token.getExpirationTime()))
				.append(Base64.getEncoder().encodeToString(token.getValue()))
				.toString();
	}
	
	public Token parse(String value) {
		ParsePosition position = new ParsePosition(0);
		LocalDateTime expirationTime = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(value, position));
		byte[] bytes = Base64.getDecoder().decode(value.substring(position.getIndex()));
		return new Token(bytes, expirationTime);
	}

}
