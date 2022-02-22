package org.training.upskilling.onlineshop.controller;

import java.io.IOException;
import java.util.Optional;

import org.eclipse.jetty.http.HttpMethod;
import org.training.upskilling.onlineshop.security.PasswordEncoder;
import org.training.upskilling.onlineshop.security.Token;
import org.training.upskilling.onlineshop.security.TokenConverter;
import org.training.upskilling.onlineshop.service.UserService;
import org.training.upskilling.onlineshop.service.dto.UserDto;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import com.fasterxml.jackson.core.JacksonException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends AbstractServlet {
	
	private static final String USER_NAME_PARAMETER = "name";
	private static final String PASSWORD_PARAMETER = "password";
	private static final String TARGET_URL_PARAMETER = "targetUrl";
	private static final String USER_TOKEN_COOKIE_NAME = "user-token";

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final TokenConverter tokenConverter;

	public LoginServlet(UserService userService, ViewGenerator viewGenerator, PasswordEncoder passwordEncoder, TokenConverter tokenConverter) {
		super(viewGenerator, true);
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.tokenConverter = tokenConverter;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, HttpMethod.POST);
	}

	@Override
	protected boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		String userName = getRequestParameter(req, USER_NAME_PARAMETER, "missing user name parameter");
		String password = getRequestParameter(req, PASSWORD_PARAMETER, "missing password parameter");
		Optional<UserDto> user = userService.findUserByName(userName);
		boolean success = user.isPresent() && passwordEncoder.matches(user.get().encodedPassword(), password);
		if(success) {
			setToken(Token.getToken(), resp);
		}
		return success;
	}

	private void setToken(Token token, HttpServletResponse resp) throws ServletException {
		try {
			resp.addCookie(new Cookie(USER_TOKEN_COOKIE_NAME, tokenConverter.toString(token)));
		} catch (JacksonException e) {
			throw new ServletException("can't convert token to string", e);
		}
	}

	@Override
	protected String getDestination(HttpServletRequest req, boolean success) throws ServletException {
		if(success) {
			return getRequestParameter(req, TARGET_URL_PARAMETER, "missing target url parameter");
		}
		return "/";
	}

}
