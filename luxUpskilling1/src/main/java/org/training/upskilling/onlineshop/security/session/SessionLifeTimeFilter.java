package org.training.upskilling.onlineshop.security.session;

import static org.training.upskilling.onlineshop.Utilities.getTokenCookieValue;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.training.upskilling.onlineshop.security.service.SecurityService;

import lombok.RequiredArgsConstructor;

@Component
@Order(2)
@RequiredArgsConstructor
public class SessionLifeTimeFilter implements Filter {

	private SecurityService securityService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		securityService.prolongSession(getTokenCookieValue(((HttpServletRequest) request).getCookies()));
		chain.doFilter(request, response);
	}

}
