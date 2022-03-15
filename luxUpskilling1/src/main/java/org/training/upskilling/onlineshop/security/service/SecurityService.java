package org.training.upskilling.onlineshop.security.service;

import java.util.Optional;

import org.training.upskilling.onlineshop.service.dto.UserDto;

public interface SecurityService {
	
	String getNewTokenValue(Optional<UserDto> user);
	boolean isValidUser(Optional<UserDto> user, String password);
	boolean hasAccess(String context, String resource, Optional<String> tokenCookieValue);

}
