package org.training.upskilling.onlineshop.controller.security;

import org.training.upskilling.onlineshop.controller.AbstractServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutServlet extends AbstractServlet {

	private static final String NO_USER_TOKEN_COOKIE_VALUE = "";

	public LogoutServlet() {
		super(true);
	}

	@Override
	protected boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		removeUserTokenCookie(resp);
		return true;
	}

	private void removeUserTokenCookie(HttpServletResponse resp) {
		Cookie cookie = new Cookie(USER_TOKEN_COOKIE_NAME, NO_USER_TOKEN_COOKIE_VALUE);
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
	}

	@Override
	protected String getDestination(HttpServletRequest req, boolean success) throws ServletException {
		return HOME_URL;
	}

}
