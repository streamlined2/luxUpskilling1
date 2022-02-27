package org.training.upskilling.onlineshop.controller;

import java.io.IOException;
import java.util.Optional;

import org.eclipse.jetty.http.HttpMethod;
import org.training.upskilling.onlineshop.security.service.SecurityService;
import org.training.upskilling.onlineshop.service.UserService;
import org.training.upskilling.onlineshop.service.dto.UserDto;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends AbstractServlet {

	public static final String USER_TOKEN_COOKIE_NAME = "user-token";
	private static final String USER_NAME_PARAMETER = "name";
	private static final String PASSWORD_PARAMETER = "password";

	private final UserService userService;
	private SecurityService securityService;

	public LoginServlet(UserService userService, SecurityService securityService, ViewGenerator viewGenerator) {
		super(viewGenerator, true);
		this.userService = userService;
		this.securityService = securityService;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, HttpMethod.POST);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, HttpMethod.GET);
	}

	@Override
	protected boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		String userName = getRequestParameter(req, USER_NAME_PARAMETER, "missing user name parameter");
		String password = getRequestParameter(req, PASSWORD_PARAMETER, "missing password parameter");
		Optional<UserDto> user = userService.findUserByName(userName);
		boolean success = user.isPresent() && securityService.matches(user.get().encodedPassword(), password);
		if (success) {
			setNewToken(resp, user.get());
		}
		return success;
	}

	private void setNewToken(HttpServletResponse resp, UserDto user) {
		resp.addCookie(new Cookie(USER_TOKEN_COOKIE_NAME, securityService.toString(securityService.createToken(user))));
	}

	@Override
	protected String getDestination(HttpServletRequest req, boolean success) throws ServletException {
		if (success) {
			return getRequestParameter(req, TARGET_URL_ATTRIBUTE, "missing target url parameter");
		}
		return "/";
	}

}
