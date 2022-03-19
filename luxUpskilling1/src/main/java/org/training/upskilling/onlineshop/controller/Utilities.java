package org.training.upskilling.onlineshop.controller;

import static org.training.upskilling.onlineshop.controller.AbstractServlet.USER_TOKEN_COOKIE_NAME;

import java.util.Arrays;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utilities {

	public static Optional<String> getTokenCookieValue(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		return cookies == null ? Optional.empty()
				: Arrays.stream(cookies).filter(cookie -> USER_TOKEN_COOKIE_NAME.equals(cookie.getName())).findFirst()
						.map(Cookie::getValue);
	}

}
