package org.training.upskilling.onlineshop.security.token;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.training.upskilling.onlineshop.security.MissingAlgorithmException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenConverter {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	private static final String SHA_256 = "SHA-256";
	private static final int SIGNATURE_LENGTH_DIGITS = 4;
	private final MessageDigest digester;

	public TokenConverter() {
		try {
			digester = MessageDigest.getInstance(SHA_256);
		} catch (NoSuchAlgorithmException e) {
			throw new MissingAlgorithmException(String.format("no such algorithm %s", SHA_256), e);
		}
	}

	private String getSignature(Token token) {
		return getSignature(token.getExpirationTime(), token.getValue());
	}

	private String getSignature(LocalDateTime expirationTime, byte[] value) {
		digester.update(Long.toString(expirationTime.toEpochSecond(ZoneOffset.UTC)).getBytes());
		digester.update(Base64.getEncoder().encodeToString(value).getBytes());
		return Base64.getEncoder().encodeToString(digester.digest());
	}

	public String toString(Token token) {
		String dateTimeValue = DATE_TIME_FORMATTER.format(token.getExpirationTime());
		String tokenValue = Base64.getEncoder().encodeToString(token.getValue());
		String signature = getSignature(token);
		return new StringBuilder().append(String.format("%0" + SIGNATURE_LENGTH_DIGITS + "d", signature.length()))
				.append(signature).append(dateTimeValue).append(tokenValue).toString();
	}

	public Token parse(String value) {
		int signatureLength = Integer.parseInt(value.substring(0, SIGNATURE_LENGTH_DIGITS));
		ParsePosition position = new ParsePosition(SIGNATURE_LENGTH_DIGITS + signatureLength);
		String signature = value.substring(SIGNATURE_LENGTH_DIGITS, position.getIndex());
		LocalDateTime expirationTime = LocalDateTime.from(DATE_TIME_FORMATTER.parse(value, position));
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
