package org.training.upskilling.onlineshop.controller.security;

import static org.training.upskilling.onlineshop.Utilities.getRequestURL;
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
import org.training.upskilling.onlineshop.controller.product.AuthenticationController;
import org.training.upskilling.onlineshop.security.service.SecurityService;

import lombok.RequiredArgsConstructor;

@Component
@Order(1)
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

	private SecurityService securityService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (securityService.hasAccess(req.getContextPath(), req.getRequestURI(),
				getTokenCookieValue(req.getCookies()))) {
			chain.doFilter(request, response);
		} else {
			request.setAttribute(AuthenticationController.TARGET_URL_ATTRIBUTE, getRequestURL(req));
			request.getRequestDispatcher("/loginform").forward(request, response);
		}
	}

}
