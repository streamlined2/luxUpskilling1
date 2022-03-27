package org.training.upskilling.onlineshop.controller.product;

import static org.training.upskilling.onlineshop.Utilities.getTokenCookieValue;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.training.upskilling.onlineshop.security.service.SecurityService;
import org.training.upskilling.onlineshop.service.OrderService;
import org.training.upskilling.onlineshop.service.UserService;
import org.training.upskilling.onlineshop.service.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthenticationController {

	public static final String TARGET_URL_ATTRIBUTE = "targetUrl";
	public static final String USER_TOKEN_COOKIE_NAME = "user-token";
	public static final String LOGIN_FORM_ENDPOINT = "/loginform";
	public static final String LOGIN_ENDPOINT = "/login";
	public static final String LOGOUT_ENDPOINT = "/logout";
	private static final String HOME_URL = "/";
	private static final String USER_NAME_PARAMETER = "name";
	private static final String PASSWORD_PARAMETER = "password";
	private static final String EMPTY_USER_TOKEN_COOKIE = "";
	private static final String USER_ROLE_ATTRIBUTE = "userRole";

	private final UserService userService;
	private final SecurityService securityService;
	private final OrderService orderService;

	@GetMapping(LOGIN_FORM_ENDPOINT)
	public String loginForm(HttpServletRequest req, HttpSession session) {
		return "/login";
	}

	@PostMapping(LOGIN_ENDPOINT)
	public String login(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		String url = (String) session.getAttribute(TARGET_URL_ATTRIBUTE);
		String userName = req.getParameter(USER_NAME_PARAMETER);
		String password = req.getParameter(PASSWORD_PARAMETER);
		if (url != null && userName != null && password != null) {
			Optional<UserDto> user = userService.findUserByName(userName);
			if (securityService.isValidUser(user, password)) {
				UserDto userDto = user.get();
				setNewToken(resp, userDto);
				session.setAttribute(USER_ROLE_ATTRIBUTE, securityService.getUserRoleName(userDto));
				orderService.createOrder(userDto);
				return "redirect:" + url;
			}
		}
		return "redirect:" + HOME_URL;
	}

	private void setNewToken(HttpServletResponse resp, UserDto user) {
		Cookie tokenCookie = new Cookie(USER_TOKEN_COOKIE_NAME, securityService.getNewTokenValue(user));
		tokenCookie.setMaxAge(-1);
		resp.addCookie(tokenCookie);
	}

	@GetMapping(LOGOUT_ENDPOINT)
	@PostMapping(LOGOUT_ENDPOINT)
	public String logout(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		securityService.invalidateToken(getTokenCookieValue(req.getCookies()));
		removeUserTokenCookie(resp);
		session.removeAttribute(USER_ROLE_ATTRIBUTE);
		return "redirect:" + HOME_URL;
	}

	private void removeUserTokenCookie(HttpServletResponse resp) {
		Cookie cookie = new Cookie(USER_TOKEN_COOKIE_NAME, EMPTY_USER_TOKEN_COOKIE);
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
	}

}
