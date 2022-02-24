package org.training.upskilling.onlineshop.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class TokenConverter {
	
	private static final String SHA_256 = "SHA-256";
	private static final int SHA_256_BYTES = 32;
	private static final int SHA_256_BASE64_CHARS = 44;
	private final MessageDigest digester;

	public TokenConverter() throws NoSuchAlgorithmException {
		digester = MessageDigest.getInstance(SHA_256);
	}
	
	private String getSignature(Token token) {
		return getSignature(token.getExpirationTime(), token.getValue());
	}

	private String getSignature(LocalDateTime expirationTime, byte[] value) {
		digester.update(DateTimeFormatter.ISO_DATE_TIME.format(expirationTime).getBytes());
		digester.update(Base64.getEncoder().encodeToString(value).getBytes());
		byte[] data = digester.digest();
		return Base64.getEncoder().encodeToString(data);
	}
	
	public String toString(Token token) {
		String dateTimeValue = DateTimeFormatter.ISO_DATE_TIME.format(token.getExpirationTime());
		String tokenValue = Base64.getEncoder().encodeToString(token.getValue());
		String signature = getSignature(token);
		return new StringBuilder()
				.append(signature)
				.append(dateTimeValue)
				.append(tokenValue)
				.toString();
	}
	
	public Token parse(String value) {
		String signature = value.substring(0, SHA_256_BASE64_CHARS);
		ParsePosition position = new ParsePosition(SHA_256_BASE64_CHARS);
		LocalDateTime expirationTime = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(value, position));
		byte[] bytes = Base64.getDecoder().decode(value.substring(position.getIndex()));
		String mustBeSignature = getSignature(expirationTime, bytes);
		if(!signature.equals(mustBeSignature)) {
			throw new InvalidTokenException("this token is forged");
		}
		return new Token(bytes, expirationTime);
	}

}
