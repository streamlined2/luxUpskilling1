package org.training.upskilling.onlineshop;

import java.util.HashMap;
import java.util.Map;

import org.training.upskilling.onlineshop.dao.jdbc.JdbcConnectionFactory;
import org.training.upskilling.onlineshop.dao.jdbc.product.ProductJdbcDao;
import org.training.upskilling.onlineshop.dao.jdbc.product.ProductJdbcHelper;
import org.training.upskilling.onlineshop.dao.jdbc.user.UserJdbcDao;
import org.training.upskilling.onlineshop.dao.jdbc.user.UserJdbcHelper;
import org.training.upskilling.onlineshop.propertiesreader.EnvironmentPropertyReader;
import org.training.upskilling.onlineshop.security.PasswordEncoder;
import org.training.upskilling.onlineshop.security.service.DefaultSecurityService;
import org.training.upskilling.onlineshop.security.service.SecurityService;
import org.training.upskilling.onlineshop.security.token.TokenConverter;
import org.training.upskilling.onlineshop.service.DefaultOrderService;
import org.training.upskilling.onlineshop.service.DefaultProductService;
import org.training.upskilling.onlineshop.service.DefaultUserService;
import org.training.upskilling.onlineshop.service.OrderService;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.service.UserService;
import org.training.upskilling.onlineshop.service.mapper.ProductMapper;
import org.training.upskilling.onlineshop.service.mapper.UserMapper;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ServiceLocator {

	private final String TOKEN_LIFE_TIME = "tokenLifeTime";
	private final String TOKEN_EXTRA_LIFE_TIME = "tokenExtraTime";

	private final Map<Class<?>, Object> applicationContext = new HashMap<>();

	static {

		try (var propertyReader = new EnvironmentPropertyReader()) {

			var connectionFactory = JdbcConnectionFactory.getConnectionFactory(propertyReader);
			applicationContext.put(JdbcConnectionFactory.class, connectionFactory);

			applicationContext.put(ProductMapper.class, new ProductMapper());
			applicationContext.put(ProductJdbcHelper.class, new ProductJdbcHelper());
			applicationContext.put(ProductJdbcDao.class, new ProductJdbcDao());

			var productService = new DefaultProductService();
			applicationContext.put(ProductService.class, productService);

			applicationContext.put(UserJdbcHelper.class, new UserJdbcHelper());
			applicationContext.put(UserJdbcDao.class, new UserJdbcDao());
			applicationContext.put(UserMapper.class, new UserMapper());

			applicationContext.put(UserService.class, new DefaultUserService());

			var orderService = new DefaultOrderService(productService);
			applicationContext.put(OrderService.class, orderService);

			applicationContext.put(ViewGenerator.class, new ViewGenerator());
			applicationContext.put(PasswordEncoder.class, new PasswordEncoder());
			applicationContext.put(TokenConverter.class, new TokenConverter());

			var securityService = new DefaultSecurityService(propertyReader.getIntegerProperty(TOKEN_LIFE_TIME),
					propertyReader.getIntegerProperty(TOKEN_EXTRA_LIFE_TIME));
			applicationContext.put(SecurityService.class, securityService);

		} catch (Exception e) {
			log.error("general type exception");
			e.printStackTrace();
		}

	}

	public <T> T getInstance(Class<T> cl) {
		return cl.cast(applicationContext.get(cl));
	}

}
