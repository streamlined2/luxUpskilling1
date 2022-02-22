package org.training.upskilling.onlineshop.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordEncoder {
	
	private static final String SHA_256="SHA-256";
	private final MessageDigest digester;
	
	public PasswordEncoder() throws NoSuchAlgorithmException {
		digester = MessageDigest.getInstance(SHA_256);
	}
	
	public byte[] encode(String password) {
		return digester.digest(password.getBytes());
	}
	
	public boolean matches(String encodedPassword, String password) {
		return Arrays.equals(encodedPassword.getBytes(), encode(password));
	}

}
