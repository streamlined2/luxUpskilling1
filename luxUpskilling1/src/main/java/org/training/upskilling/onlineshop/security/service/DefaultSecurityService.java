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
	public boolean hasAccess(String context, String requestURI, Optional<String> tokenCookieValue) {
		return !isProtectedResource(context, requestURI) || tokenGrantsAccess(requestURI, tokenCookieValue);
	}

	private boolean isProtectedResource(String context, String resource) {
		return PROTECTED_RESOURCES.stream().anyMatch(protectedResource -> resource.regionMatches(context.length(),
				protectedResource, 0, protectedResource.length()));
	}

	private boolean tokenGrantsAccess(String requestURI, Optional<String> tokenCookieValue) {
		return tokenCookieValue
				.map(tokenValue -> isGranted(tokenConverter.parse(tokenValue), requestURI)).orElse(false);
	}

	private boolean isGranted(Token token, String resource) {
		return isValid(token) && tokens.containsKey(token);
	}

	private boolean isValid(Token token) {
		return token.getExpirationTime().isAfter(LocalDateTime.now());
	}

	@Override
	public String getNewTokenValue(Optional<UserDto> user) {
		return toString(createAndRegisterToken(user));
	}

	@Override
	public boolean isValidUser(Optional<UserDto> user, String password) {
		return user.map(validUser -> passwordEncoder.matches(validUser.encodedPassword(), password, validUser.salt()))
				.orElse(false);
	}

	private Token createAndRegisterToken(Optional<UserDto> user) {
		Token token = new Token(tokenLifeTime);
		tokens.put(token, user.orElseThrow(NoValidUserException::new));
		return token;
	}

	private String toString(Token token) {
		return tokenConverter.toString(token);
	}

}
