package org.training.upskilling.onlineshop.security.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.training.upskilling.onlineshop.model.User.Role;
import org.training.upskilling.onlineshop.security.PasswordEncoder;
import org.training.upskilling.onlineshop.security.session.Session;
import org.training.upskilling.onlineshop.security.token.Token;
import org.training.upskilling.onlineshop.security.token.TokenConverter;
import org.training.upskilling.onlineshop.service.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultSecurityService implements SecurityService {

	private static final Map<Role, String> PROTECTED_RESOURCES = Map.of(Role.ADMIN, "/product/add", Role.ADMIN,
			"/product/edit", Role.ADMIN, "/product/delete", Role.ADMIN, "/saveproduct", Role.USER, "/product/cart/add",
			Role.USER, "/product/cart/delete");

	private final Map<Token, Session> sessions = new ConcurrentHashMap<>();

	private final PasswordEncoder passwordEncoder;
	private final TokenConverter tokenConverter;
	private final int tokenLifeTime;

	@Override
	public boolean hasAccess(String context, String requestURI, Optional<String> tokenCookieValue) {
		return !isProtectedResource(context, requestURI) || tokenGrantsAccess(requestURI, tokenCookieValue);
	}

	private boolean isProtectedResource(String context, String resource) {
		return PROTECTED_RESOURCES.values().stream().anyMatch(protectedResource -> resource
				.regionMatches(context.length(), protectedResource, 0, protectedResource.length()));
	}

	private boolean tokenGrantsAccess(String requestURI, Optional<String> tokenCookieValue) {
		return tokenCookieValue.map(tokenValue -> isAccessGranted(tokenConverter.parse(tokenValue), requestURI))
				.orElse(false);
	}

	private boolean isAccessGranted(Token token, String resource) {
		Session session = sessions.get(token);
		if (session == null || !isValid(session)) {
			return false;
		}
		Role role = Role.getRole(session.getUser().role());
		return PROTECTED_RESOURCES.entrySet().stream()
				.anyMatch(entry -> entry.getKey().equals(role) && resource.startsWith(entry.getValue()));
	}

	private boolean isValid(Session session) {
		return session.getExpirationTime().isAfter(LocalDateTime.now());
	}

	@Override
	public String getNewTokenValue(UserDto user) {
		return tokenConverter.toString(createAndRegisterSession(user).getToken());
	}

	private Session createAndRegisterSession(UserDto user) {
		Token token = new Token();
		Session session = new Session(token, user, tokenLifeTime);
		sessions.put(token, session);
		return session;
	}

	@Override
	public boolean isValidUser(Optional<UserDto> user, String password) {
		return user.map(validUser -> passwordEncoder.matches(validUser.encodedPassword(), password, validUser.salt()))
				.orElse(false);
	}

}
