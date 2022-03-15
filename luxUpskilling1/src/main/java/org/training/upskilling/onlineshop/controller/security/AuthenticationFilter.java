package org.training.upskilling.onlineshop.controller.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import org.training.upskilling.onlineshop.controller.AbstractServlet;
import org.training.upskilling.onlineshop.security.service.SecurityService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import static org.training.upskilling.onlineshop.controller.AbstractServlet.USER_TOKEN_COOKIE_NAME;

@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

	private final SecurityService securityService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (securityService.hasAccess(req.getContextPath(), req.getRequestURI(),
				getTokenCookieValue(req.getCookies()))) {
			chain.doFilter(request, response);
		} else {
			request.setAttribute(AbstractServlet.TARGET_URL_ATTRIBUTE, getRequestURL(req));
			request.getRequestDispatcher("/loginform").forward(request, response);
		}
	}

	private Optional<String> getTokenCookieValue(Cookie[] cookies) {
		return cookies == null ? Optional.empty()
				: Arrays.stream(cookies).filter(cookie -> USER_TOKEN_COOKIE_NAME.equals(cookie.getName())).findFirst()
						.map(Cookie::getValue);
	}

	private String getRequestURL(HttpServletRequest req) {
		StringBuilder buf = new StringBuilder(req.getServletPath());
		buf.append(req.getPathInfo());
		String query = req.getQueryString();
		if (query != null) {
			buf.append(query);
		}
		return buf.toString();
	}

}
