package org.training.upskilling.onlineshop.security.service;

import java.util.Optional;

import org.training.upskilling.onlineshop.service.dto.UserDto;

public interface SecurityService {
	
	String getNewTokenValue(UserDto user);
	String getUserRoleName(UserDto user);
	Optional<UserDto> getUser(Optional<String> tokenCookieValue);
	boolean isValidUser(Optional<UserDto> user, String password);
	boolean hasAccess(String context, String resource, Optional<String> tokenCookieValue);
	void prolongSession(Optional<String> tokenCookieValue);
	void invalidateToken(Optional<String> tokenCookieValue);

}
