package org.training.upskilling.onlineshop;

import java.util.Arrays;
import java.util.Optional;

import org.training.upskilling.onlineshop.controller.product.AuthenticationController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utilities {

	public Optional<String> getTokenCookieValue(Cookie[] cookies) {
		return cookies == null ? Optional.empty()
				: Arrays.stream(cookies).filter(cookie -> AuthenticationController.USER_TOKEN_COOKIE_NAME.equals(cookie.getName())).findFirst()
						.map(Cookie::getValue);
	}

	public String getRequestURL(HttpServletRequest req) {
		StringBuilder buf = new StringBuilder(req.getServletPath());
		String pathInfo = req.getPathInfo();
		if (pathInfo != null) {
			buf.append(pathInfo);
		}
		String query = req.getQueryString();
		if (query != null) {
			buf.append(query);
		}
		return buf.toString();
	}

}
