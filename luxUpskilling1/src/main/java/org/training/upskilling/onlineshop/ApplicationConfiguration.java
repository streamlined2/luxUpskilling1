package org.training.upskilling.onlineshop;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.training.upskilling.onlineshop.dao.jdbc.product.ProductJdbcHelper;
import org.training.upskilling.onlineshop.dao.jdbc.user.UserJdbcHelper;
import org.training.upskilling.onlineshop.security.PasswordEncoder;
import org.training.upskilling.onlineshop.security.token.TokenConverter;
import org.training.upskilling.onlineshop.service.mapper.ProductMapper;
import org.training.upskilling.onlineshop.service.mapper.UserMapper;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan({ "org.training.upskilling.onlineshop.dao", "org.training.upskilling.onlineshop.service",
		"org.training.upskilling.onlineshop.filter" })
public class ApplicationConfiguration {

	@Bean
	public DataSource dataSource(@Value("${url}") String url, @Value("${user}") String user,
			@Value("${password}") String password, @Value("${minIdle}") int minIdle, @Value("${maxIdle}") int maxIdle,
			@Value("${maxOpenPreparedStatements}") int maxOpenPreparedStatements) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxIdle(maxIdle);
		dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		return dataSource;
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
