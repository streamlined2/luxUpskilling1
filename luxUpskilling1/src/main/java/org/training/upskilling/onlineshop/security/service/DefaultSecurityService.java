package org.training.upskilling.onlineshop.security.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;
import org.training.upskilling.onlineshop.ServiceLocator;
import org.training.upskilling.onlineshop.model.User.Role;
import org.training.upskilling.onlineshop.security.PasswordEncoder;
import org.training.upskilling.onlineshop.security.session.Session;
import org.training.upskilling.onlineshop.security.token.Token;
import org.training.upskilling.onlineshop.security.token.TokenConverter;
import org.training.upskilling.onlineshop.service.dto.UserDto;

@Service
public class DefaultSecurityService implements SecurityService {

	private static final Map<String, Role> PROTECTED_RESOURCES = Map.of("/product/add", Role.ADMIN, "/product/edit",
			Role.ADMIN, "/product/delete", Role.ADMIN, "/saveproduct", Role.ADMIN, "/product/cart/add", Role.USER,
			"/product/cart/delete", Role.USER);
	private static final String NO_USER_ROLE = "";

	private final Map<Token, Session> sessions;
	private final ExecutorService cleaner;

	private final PasswordEncoder passwordEncoder = ServiceLocator.getInstance(PasswordEncoder.class);
	private final TokenConverter tokenConverter = ServiceLocator.getInstance(TokenConverter.class);
	private final int tokenLifeTime;
	private final int tokenExtraTime;

	public DefaultSecurityService(int tokenLifeTime, int tokenExtraTime) {
		this.tokenLifeTime = tokenLifeTime;
		this.tokenExtraTime = tokenExtraTime;
		sessions = new ConcurrentHashMap<>();
		cleaner = Executors.newSingleThreadExecutor();
		cleaner.submit(new Cleaner());
	}

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
		Optional<Session> session = getSession(token);
		if (session.isEmpty()) {
			return false;
		}
		Role role = Role.getRole(session.get().getUser().role());
		return isRoleMatchesProtectedResource(role, context, resource);
	}

	private Optional<Session> getSession(Token token) {
		Session session = sessions.get(token);
		if (session == null || !isValid(session)) {
			return Optional.empty();
		}
		return Optional.of(session);
	}

	private boolean isValid(Session session) {
		return session.getExpirationTime().isAfter(LocalDateTime.now());
	}

	private boolean isRoleMatchesProtectedResource(Role role, String context, String resource) {
		return PROTECTED_RESOURCES.entrySet().stream().anyMatch(entry -> entry.getValue().equals(role)
				&& resource.regionMatches(context.length(), entry.getKey(), 0, entry.getKey().length()));
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

	@Override
	public void prolongSession(Optional<String> tokenCookieValue) {
		Optional<Session> session = tokenCookieValue.map(tokenConverter::parse).flatMap(this::getSession);
		session.ifPresent(s -> s.prolongExpirationTime(tokenExtraTime));
	}

	@Override
	public Optional<UserDto> getUser(Optional<String> tokenCookieValue) {
		return tokenCookieValue.flatMap(tokenValue -> checkSessionAndGetUser(tokenConverter.parse(tokenValue)));
	}

	private Optional<UserDto> checkSessionAndGetUser(Token token) {
		Session session = sessions.get(token);
		if (session == null || !isValid(session)) {
			return Optional.empty();
		}
		return Optional.of(session.getUser());
	}

	private class Cleaner implements Runnable {

		@Override
		public void run() {
			for (var i = sessions.entrySet().iterator(); !i.hasNext();) {
				if (!isValid(i.next().getValue())) {
					i.remove();
				}
			}
		}

	}

}
