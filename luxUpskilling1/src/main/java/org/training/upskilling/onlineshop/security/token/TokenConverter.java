package org.training.upskilling.onlineshop.security.token;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

import org.training.upskilling.onlineshop.security.MissingAlgorithmException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenConverter {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	private static final String SHA_256 = "SHA-256";
	private static final int SIGNATURE_LENGTH_DIGITS = 3;

	private final MessageDigest digester;

	public TokenConverter() {
		try {
			digester = MessageDigest.getInstance(SHA_256);
		} catch (NoSuchAlgorithmException e) {
			throw new MissingAlgorithmException(String.format("no such algorithm as %s", SHA_256), e);
		}
	}

	private String getSignature(Token token) {
		return getSignature(token.getExpirationTime(), token.getUuid());
	}

	private String getSignature(LocalDateTime expirationTime, UUID uuid) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(baos)) {
			dos.writeLong(expirationTime.toEpochSecond(ZoneOffset.UTC));
			dos.writeChars(uuid.toString());
			return Base64.getEncoder().encodeToString(digester.digest(baos.toByteArray()));
		} catch (IOException e) {
			log.error("can't determine signature for expiration time {} and uuid {}", expirationTime, uuid);
			throw new TokenConversionException(
					String.format("can't determine signature for expiration time %s and uuid %s",
							DATE_TIME_FORMATTER.format(expirationTime), uuid.toString()),
					e);
		}
	}

	public String toString(Token token) {
		String signature = getSignature(token);
		return String.format("%0" + SIGNATURE_LENGTH_DIGITS + "d%s%s%s", signature.length(), signature,
				DATE_TIME_FORMATTER.format(token.getExpirationTime()), token.getUuid().toString());
	}

	public Token parse(String value) {
		int signatureLength = Integer.parseInt(value.substring(0, SIGNATURE_LENGTH_DIGITS));
		ParsePosition position = new ParsePosition(SIGNATURE_LENGTH_DIGITS + signatureLength);
		String actualSignature = value.substring(SIGNATURE_LENGTH_DIGITS, position.getIndex());
		LocalDateTime expirationTime = LocalDateTime.from(DATE_TIME_FORMATTER.parse(value, position));
		UUID uuid = UUID.fromString(value.substring(position.getIndex()));
		String mustBeSignature = getSignature(expirationTime, uuid);
		if (actualSignature.equals(mustBeSignature)) {
			return new Token(uuid, expirationTime);
		}
		log.error("token {} is forged", value);
		throw new InvalidTokenException(String.format("token %s is forged", value));
	}

}
