package org.training.upskilling.onlineshop;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("org.training.upskilling.onlineshop.controller")
public class WebConfiguration implements WebMvcConfigurer {

	@Bean
	public FreeMarkerConfigurer viewConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/WEB-INF/templates");
		configurer.setDefaultEncoding("UTF-8");
		Properties settings = new Properties();
		settings.put("default_encoding", "UTF-8");
		configurer.setFreemarkerSettings(settings);
		return configurer;
	}

	@Bean
	public FreeMarkerViewResolver viewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setSuffix(".ftl");
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setCache(false);
		resolver.setRequestContextAttribute("rc");
		return resolver;
	}

}
