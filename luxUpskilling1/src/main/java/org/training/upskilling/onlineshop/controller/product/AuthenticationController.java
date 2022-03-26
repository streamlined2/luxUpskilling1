package org.training.upskilling.onlineshop.controller.product;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.training.upskilling.onlineshop.security.service.SecurityService;
import org.training.upskilling.onlineshop.service.OrderService;
import org.training.upskilling.onlineshop.service.UserService;
import org.training.upskilling.onlineshop.service.dto.UserDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthenticationController {

	public static final String TARGET_URL_ATTRIBUTE = "targetUrl";
	public static final String USER_TOKEN_COOKIE_NAME = "user-token";
	private static final String HOME_URL = "/";
	private static final String USER_NAME_PARAMETER = "name";
	private static final String PASSWORD_PARAMETER = "password";
	private static final String EMPTY_USER_TOKEN_COOKIE = "";

	private final UserService userService;
	private final SecurityService securityService;
	private final OrderService orderService;

	@GetMapping("/loginform")
	public String loginForm(HttpServletRequest req) {
		return "/login";
	}

	@GetMapping("/login")
	public String login(HttpServletRequest req, HttpServletResponse resp) {
		String url = (String) req.getAttribute(TARGET_URL_ATTRIBUTE);
		String userName = req.getParameter(USER_NAME_PARAMETER);
		String password = req.getParameter(PASSWORD_PARAMETER);
		if (url != null && userName != null && password != null) {
			Optional<UserDto> user = userService.findUserByName(userName);
			if (securityService.isValidUser(user, password)) {
				UserDto userDto = user.get();
				setNewToken(resp, userDto);
				orderService.createOrder(userDto);
				return url;
			}
		}
		return HOME_URL;
	}

	private void setNewToken(HttpServletResponse resp, UserDto user) {
		Cookie tokenCookie = new Cookie(USER_TOKEN_COOKIE_NAME, securityService.getNewTokenValue(user));
		tokenCookie.setMaxAge(-1);
		resp.addCookie(tokenCookie);
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletResponse resp) {
		removeUserTokenCookie(resp);		
		return HOME_URL;
	}

	private void removeUserTokenCookie(HttpServletResponse resp) {
		Cookie cookie = new Cookie(USER_TOKEN_COOKIE_NAME, EMPTY_USER_TOKEN_COOKIE);
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
	}

}