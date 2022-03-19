package org.training.upskilling.onlineshop.security.session;

import java.io.IOException;

import org.training.upskilling.onlineshop.controller.Utilities;
import org.training.upskilling.onlineshop.security.service.SecurityService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SessionLifeTimeFilter implements Filter {

	private final SecurityService securityService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		securityService.prolongSession(Utilities.getTokenCookieValue((HttpServletRequest) request));
		chain.doFilter(request, response);
	}

}
