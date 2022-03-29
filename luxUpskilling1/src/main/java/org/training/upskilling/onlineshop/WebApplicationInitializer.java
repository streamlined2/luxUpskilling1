package org.training.upskilling.onlineshop;

import javax.servlet.Filter;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { ApplicationConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		DelegatingFilterProxy authenticationFilter = new DelegatingFilterProxy();
		authenticationFilter.setTargetBeanName("authenticationFilter");
		DelegatingFilterProxy sessionLifeTimeFilter = new DelegatingFilterProxy();
		sessionLifeTimeFilter.setTargetBeanName("sessionLifeTimeFilter");
		return new Filter[] { authenticationFilter, sessionLifeTimeFilter };
	}

}
