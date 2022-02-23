package org.training.upskilling.onlineshop.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.training.upskilling.onlineshop.controller.AbstractServlet;
import org.training.upskilling.onlineshop.controller.LoginServlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

	private final TokenConverter tokenConverter;

	private static final Set<String> PROTECTED_RESOURCES = Set.of("/products/add", "/products/edit", "/products/delete",
			"/saveproduct");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (!isProtected(req.getContextPath(), req.getRequestURI()) || hasAccessRights(req.getCookies())) {
			chain.doFilter(request, response);
		} else {
			request.setAttribute(AbstractServlet.TARGET_URL_ATTRIBUTE, getRequestURL(req));
			request.getRequestDispatcher("/loginform").forward(request, response);
		}
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

	private boolean hasAccessRights(Cookie[] cookies) throws ServletException {
		Optional<String> tokenValue = Arrays.stream(cookies)
				.filter(cookie -> LoginServlet.USER_TOKEN_COOKIE_NAME.equals(cookie.getName())).findFirst()
				.map(Cookie::getValue);
		if (tokenValue.isPresent()) {
			Token token = tokenConverter.parse(tokenValue.get());
			if (token.isValid()) {
				return true;
			}
		}
		return false;
	}

	private boolean isProtected(String context, String url) {
		for (String protectedUrl : PROTECTED_RESOURCES) {
			if (url.regionMatches(context.length(), protectedUrl, 0, protectedUrl.length())) {
				return true;
			}
		}
		return false;
	}

}
