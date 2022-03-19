package org.training.upskilling.onlineshop.controller.security;

import java.util.Optional;

import org.training.upskilling.onlineshop.controller.AbstractServlet;
import org.training.upskilling.onlineshop.security.service.SecurityService;
import org.training.upskilling.onlineshop.service.OrderService;
import org.training.upskilling.onlineshop.service.UserService;
import org.training.upskilling.onlineshop.service.dto.UserDto;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends AbstractServlet {

	private static final String HOME_URL = "/";
	private static final String USER_NAME_PARAMETER = "name";
	private static final String PASSWORD_PARAMETER = "password";

	private final UserService userService;
	private final SecurityService securityService;
	private final OrderService orderService;

	public LoginServlet(UserService userService, SecurityService securityService, OrderService orderService,
			ViewGenerator viewGenerator) {
		super(securityService, viewGenerator, true);
		this.userService = userService;
		this.securityService = securityService;
		this.orderService = orderService;
	}

	@Override
	protected boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		String userName = getRequestParameter(req, USER_NAME_PARAMETER, "missing user name parameter");
		String password = getRequestParameter(req, PASSWORD_PARAMETER, "missing password parameter");
		Optional<UserDto> user = userService.findUserByName(userName);
		if (securityService.isValidUser(user, password)) {
			UserDto userDto = user.get();
			setNewToken(resp, userDto);
			orderService.createOrder(userDto);
			clearCartAttribute();
			return true;
		}
		return false;
	}

	private void setNewToken(HttpServletResponse resp, UserDto user) {
		Cookie tokenCookie = new Cookie(USER_TOKEN_COOKIE_NAME, securityService.getNewTokenValue(user));
		tokenCookie.setMaxAge(-1);
		resp.addCookie(tokenCookie);
	}

	@Override
	protected String getDestination(HttpServletRequest req, boolean success) throws ServletException {
		if (success) {
			return getRequestParameter(req, TARGET_URL_ATTRIBUTE, "missing target url parameter");
		}
		return HOME_URL;
	}

}
