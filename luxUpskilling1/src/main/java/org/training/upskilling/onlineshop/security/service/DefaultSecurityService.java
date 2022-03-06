package org.training.upskilling.onlineshop.security.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
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
		return PROTECTED_RESOURCES.stream().anyMatch(
				protectedResource -> resource.regionMatches(context.length(), protectedResource, 0, protectedResource.length()));
	}

	@Override
	public String newTokenValue(UserDto user) {
		return toString(createAndRegisterToken(user));
	}

	@Override
	public boolean isValidUser(Optional<UserDto> user, String password) {
		return user.map(validUser -> passwordEncoder.matches(validUser.encodedPassword(), password)).orElse(false);
	}

	@Override
	public boolean retrieveTokenAndCheckAccess(String tokenValue, String resource) {
		return isGranted(tokenConverter.parse(tokenValue), resource);
	}

	private Token createAndRegisterToken(UserDto user) {
		Token token = new Token(tokenLifeTime);
		tokens.put(token, user);
		return token;
	}

	private String toString(Token token) {
		return tokenConverter.toString(token);
	}

	private boolean isGranted(Token token, String resource) {
		return isValid(token) && tokens.containsKey(token);
	}

	private boolean isValid(Token token) {
		return token.getExpirationTime().isAfter(LocalDateTime.now());
	}

}
