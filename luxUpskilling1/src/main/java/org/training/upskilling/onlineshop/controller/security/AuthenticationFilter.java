package org.training.upskilling.onlineshop.controller.security;

import static org.training.upskilling.onlineshop.Utilities.getRequestURL;
import static org.training.upskilling.onlineshop.Utilities.getTokenCookieValue;

import java.io.IOException;

import org.training.upskilling.onlineshop.ServiceLocator;
import org.training.upskilling.onlineshop.controller.product.AuthenticationController;
import org.training.upskilling.onlineshop.security.service.SecurityService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationFilter implements Filter {

	private final SecurityService securityService = ServiceLocator.getInstance(SecurityService.class);

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
