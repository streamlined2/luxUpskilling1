package org.training.upskilling.onlineshop.security.service;

import java.util.Optional;

import org.training.upskilling.onlineshop.service.dto.UserDto;

public interface SecurityService {
	
	String newTokenValue(UserDto user);
	boolean retrieveTokenAndCheckAccess(String tokenValue, String resource);
	boolean isValidUser(Optional<UserDto> user, String password);
	boolean isProtectedResource(String context, String resource);

}
