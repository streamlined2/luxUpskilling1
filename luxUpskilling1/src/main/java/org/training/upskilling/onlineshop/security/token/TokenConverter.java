package org.training.upskilling.onlineshop.security.token;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenConverter {

	private static final String SHA_256 = "SHA-256";
	private static final int SIGNATURE_LENGTH_DIGITS = 4;
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
		return new StringBuilder().append(String.format("%0" + SIGNATURE_LENGTH_DIGITS + "d", signature.length()))
				.append(signature).append(dateTimeValue).append(tokenValue).toString();
	}

	public Token parse(String value) {
		int signatureLength = Integer.parseInt(value.substring(0, SIGNATURE_LENGTH_DIGITS));
		String signature = value.substring(SIGNATURE_LENGTH_DIGITS, SIGNATURE_LENGTH_DIGITS + signatureLength);
		ParsePosition position = new ParsePosition(SIGNATURE_LENGTH_DIGITS + signatureLength);
		LocalDateTime expirationTime = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(value, position));
		try {
			byte[] bytes = Base64.getDecoder().decode(value.substring(position.getIndex()));
			String mustBeSignature = getSignature(expirationTime, bytes);
			if (!signature.equals(mustBeSignature)) {
				log.error("token {} is forged", value);
				throw new InvalidTokenException(String.format("token %s is forged", value));
			}
			return new Token(bytes, expirationTime);
		} catch (IllegalArgumentException e) {
			log.error("invalid token format of token {}", value);
			throw new InvalidTokenException(String.format("invalid format of token %s", value));
		}
	}

}
