package org.training.upskilling.onlineshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.training.upskilling.onlineshop.dao.jdbc.JdbcConnectionFactory;
import org.training.upskilling.onlineshop.dao.jdbc.product.ProductJdbcHelper;
import org.training.upskilling.onlineshop.dao.jdbc.user.UserJdbcHelper;
import org.training.upskilling.onlineshop.security.PasswordEncoder;
import org.training.upskilling.onlineshop.security.token.TokenConverter;
import org.training.upskilling.onlineshop.service.mapper.ProductMapper;
import org.training.upskilling.onlineshop.service.mapper.UserMapper;

@Configuration
@PropertySource("classpath:/application.properties")
public class ApplicationConfiguration {
	
	@Bean
	public JdbcConnectionFactory connectionFactory(@Value("${url}") String url, @Value("${user}") String user,
			@Value("${password}") String password, @Value("${minIdle}") int minIdle, @Value("${maxIdle}") int maxIdle,
			@Value("${maxOpenPreparedStatements}") int maxOpenPreparedStatements) {
		return new JdbcConnectionFactory(url, user, password, minIdle, maxIdle, maxOpenPreparedStatements);
	}

	@Bean
	public ProductJdbcHelper productJdbcHelper() {
		return new ProductJdbcHelper();
	}

	@Bean
	public UserJdbcHelper userJdbcHelper() {
		return new UserJdbcHelper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder();
	}

	@Bean
	public TokenConverter tokenConverter() {
		return new TokenConverter();
	}

	@Bean
	public ProductMapper productMapper() {
		return new ProductMapper();
	}
	
	@Bean
	public UserMapper userMapper() {
		return new UserMapper();
	}
	
}
