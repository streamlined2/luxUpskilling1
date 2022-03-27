package org.training.upskilling.onlineshop.controller.security;

import static org.training.upskilling.onlineshop.Utilities.getRequestURL;
import static org.training.upskilling.onlineshop.Utilities.getTokenCookieValue;
import static org.training.upskilling.onlineshop.controller.product.AuthenticationController.LOGIN_ENDPOINT;
import static org.training.upskilling.onlineshop.controller.product.AuthenticationController.LOGIN_FORM_ENDPOINT;
import static org.training.upskilling.onlineshop.controller.product.AuthenticationController.LOGOUT_ENDPOINT;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.training.upskilling.onlineshop.controller.product.AuthenticationController;
import org.training.upskilling.onlineshop.security.service.SecurityService;

@Component("authenticationFilter")
@Order(1)
public class AuthenticationFilter implements Filter {

	private static final Set<String> USER_LOG_ENDPOINTS = Set.of(LOGIN_ENDPOINT, LOGIN_FORM_ENDPOINT, LOGOUT_ENDPOINT);

	@Autowired
	private SecurityService securityService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (securityService.hasAccess(req.getContextPath(), req.getRequestURI(),
				getTokenCookieValue(req.getCookies()))) {
			saveTargetUrl(req);
			chain.doFilter(request, response);
		} else {
			req.getRequestDispatcher("/loginform").forward(request, response);
		}
	}

	private void saveTargetUrl(HttpServletRequest req) {
		String targetUrl = getRequestURL(req);
		if (USER_LOG_ENDPOINTS.contains(targetUrl)) {
			return;
		}
		req.getSession().setAttribute(AuthenticationController.TARGET_URL_ATTRIBUTE, targetUrl);
	}

}
