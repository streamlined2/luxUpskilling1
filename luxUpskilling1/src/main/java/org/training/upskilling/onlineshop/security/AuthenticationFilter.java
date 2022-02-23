package org.training.upskilling.onlineshop.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.training.upskilling.onlineshop.controller.LoginServlet;

import com.fasterxml.jackson.core.JacksonException;

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
			req.setAttribute(LoginServlet.TARGET_URL_ATTRIBUTE, req.getRequestURL());
			req.getRequestDispatcher("/loginform").forward(request, response);
		}
	}

	private boolean hasAccessRights(Cookie[] cookies) throws ServletException {
		try {
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
		} catch (JacksonException e) {
			throw new ServletException("invalid token cannot be parsed", e);
		}
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
