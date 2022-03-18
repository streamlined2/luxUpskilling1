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

	private static final String NO_USER_ROLE = "";
	private static final Map<String, Role> PROTECTED_RESOURCES = Map.of("/product/add", Role.ADMIN, "/product/edit",
			Role.ADMIN, "/product/delete", Role.ADMIN, "/saveproduct", Role.ADMIN, "/product/cart/add", Role.USER,
			"/product/cart/delete", Role.USER);

	private final Map<Token, Session> sessions = new ConcurrentHashMap<>();

	private final PasswordEncoder passwordEncoder;
	private final TokenConverter tokenConverter;
	private final int tokenLifeTime;

	@Override
	public boolean hasAccess(String context, String requestURI, Optional<String> tokenCookieValue) {
		return !isProtectedResource(context, requestURI) || tokenGrantsAccess(context, requestURI, tokenCookieValue);
	}

	private boolean isProtectedResource(String context, String resource) {
		return PROTECTED_RESOURCES.keySet().stream().anyMatch(protectedResource -> resource
				.regionMatches(context.length(), protectedResource, 0, protectedResource.length()));
	}

	private boolean tokenGrantsAccess(String context, String requestURI, Optional<String> tokenCookieValue) {
		return tokenCookieValue
				.map(tokenValue -> isAccessGranted(tokenConverter.parse(tokenValue), context, requestURI))
				.orElse(false);
	}

	private boolean isAccessGranted(Token token, String context, String resource) {
		Session session = sessions.get(token);
		if (session == null || !isValid(session)) {
			return false;
		}
		Role role = Role.getRole(session.getUser().role());
		return isRoleMatchesProtectedResource(role, context, resource);
	}

	private boolean isRoleMatchesProtectedResource(Role role, String context, String resource) {
		return PROTECTED_RESOURCES.entrySet().stream().anyMatch(entry -> entry.getValue().equals(role)
				&& resource.regionMatches(context.length(), entry.getKey(), 0, entry.getKey().length()));
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

	@Override
	public String getUserRoleName(Optional<String> tokenCookieValue) {
		return tokenCookieValue.map(tokenValue -> checkSessionAndGetRoleName(tokenConverter.parse(tokenValue)))
				.orElse(NO_USER_ROLE);
	}

	private String checkSessionAndGetRoleName(Token token) {
		Session session = sessions.get(token);
		if (session == null || !isValid(session)) {
			return NO_USER_ROLE;
		}
		Role role = Role.getRole(session.getUser().role());
		return role.name();
	}

}
