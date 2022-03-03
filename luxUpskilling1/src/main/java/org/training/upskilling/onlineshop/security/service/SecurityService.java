package org.training.upskilling.onlineshop.security.service;

import org.training.upskilling.onlineshop.security.token.Token;
import org.training.upskilling.onlineshop.service.dto.UserDto;

public interface SecurityService {
	
	Token createToken(UserDto user);
	Token parse(String string);
	String toString(Token token);
	boolean matches(String encodedPassword, String password);
	boolean isProtectedResource(String context, String resource);
	boolean granted(Token token, String resource);

}