package org.training.upskilling.onlineshop.security.session;

import static org.training.upskilling.onlineshop.Utilities.getTokenCookieValue;

import java.io.IOException;

import org.training.upskilling.onlineshop.ServiceLocator;
import org.training.upskilling.onlineshop.security.service.SecurityService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class SessionLifeTimeFilter implements Filter {

	private final SecurityService securityService = ServiceLocator.getInstance(SecurityService.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		securityService.prolongSession(getTokenCookieValue(((HttpServletRequest) request).getCookies()));
		chain.doFilter(request, response);
	}

}