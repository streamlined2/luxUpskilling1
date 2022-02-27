package org.training.upskilling.onlineshop.security.service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.training.upskilling.onlineshop.security.PasswordEncoder;
import org.training.upskilling.onlineshop.security.token.Token;
import org.training.upskilling.onlineshop.security.token.TokenConverter;
import org.training.upskilling.onlineshop.service.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultSecurityService implements SecurityService {

	private static final Set<String> PROTECTED_RESOURCES = Set.of("/products/add", "/products/edit", "/products/delete",
			"/saveproduct");

	private final Map<Token, UserDto> tokens = new ConcurrentHashMap<>();

	private final PasswordEncoder passwordEncoder;
	private final TokenConverter tokenConverter;
	private final int tokenLifeTime;

	@Override
	public boolean isProtectedResource(String context, String resource) {
		for (String protectedResource : PROTECTED_RESOURCES) {
			if (resource.regionMatches(context.length(), protectedResource, 0, protectedResource.length())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Token createToken(UserDto user) {
		Token token = new Token(tokenLifeTime);
		tokens.put(token, user);
		return token;
	}

	@Override
	public Token parse(String string) {
		return tokenConverter.parse(string);
	}

	@Override
	public String toString(Token token) {
		return tokenConverter.toString(token);
	}

	@Override
	public boolean matches(String encodedPassword, String password) {
		return passwordEncoder.matches(encodedPassword, password);
	}

	@Override
	public boolean granted(Token token, String resource) {
		return token.isValid() && tokens.containsKey(token);
	}

}
