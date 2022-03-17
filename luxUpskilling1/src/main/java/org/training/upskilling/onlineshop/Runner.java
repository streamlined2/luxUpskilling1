package org.training.upskilling.onlineshop;

import java.util.EnumSet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.training.upskilling.onlineshop.controller.product.CreateProductServlet;
import org.training.upskilling.onlineshop.controller.product.DeleteProductServlet;
import org.training.upskilling.onlineshop.controller.product.ListAllProductsServlet;
import org.training.upskilling.onlineshop.controller.product.ModifyProductServlet;
import org.training.upskilling.onlineshop.controller.product.SaveProductServlet;
import org.training.upskilling.onlineshop.controller.security.AuthenticationFilter;
import org.training.upskilling.onlineshop.controller.security.LoginFormServlet;
import org.training.upskilling.onlineshop.controller.security.LoginServlet;
import org.training.upskilling.onlineshop.dao.jdbc.JdbcConnectionFactory;
import org.training.upskilling.onlineshop.dao.jdbc.product.ProductJdbcDao;
import org.training.upskilling.onlineshop.dao.jdbc.product.ProductJdbcHelper;
import org.training.upskilling.onlineshop.dao.jdbc.user.UserJdbcDao;
import org.training.upskilling.onlineshop.dao.jdbc.user.UserJdbcHelper;
import org.training.upskilling.onlineshop.propertiesreader.EnvironmentPropertyReader;
import org.training.upskilling.onlineshop.security.PasswordEncoder;
import org.training.upskilling.onlineshop.security.service.DefaultSecurityService;
import org.training.upskilling.onlineshop.security.token.TokenConverter;
import org.training.upskilling.onlineshop.service.DefaultOrderService;
import org.training.upskilling.onlineshop.service.DefaultProductService;
import org.training.upskilling.onlineshop.service.DefaultUserService;
import org.training.upskilling.onlineshop.service.mapper.ProductMapper;
import org.training.upskilling.onlineshop.service.mapper.UserMapper;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Runner {

	private static final String CONTEXT = "/onlineshop";
	private static final String SERVER_PORT = "port";
	private static final String TOKEN_LIFE_TIME = "tokenLifeTime";

	public static void main(String[] args) {

		try (var propertyReader = new EnvironmentPropertyReader();
				var connectionFactory = JdbcConnectionFactory.getConnectionFactory(propertyReader)) {

			var orderService = new DefaultOrderService();
			
			var productService = new DefaultProductService(
					new ProductJdbcDao(connectionFactory, new ProductJdbcHelper()), new ProductMapper());

			var userService = new DefaultUserService(new UserJdbcDao(connectionFactory, new UserJdbcHelper()),
					new UserMapper());

			var viewGenerator = new ViewGenerator();

			var securityService = new DefaultSecurityService(new PasswordEncoder(), new TokenConverter(),
					propertyReader.getIntegerProperty(TOKEN_LIFE_TIME));

			var server = new Server(Integer.parseInt(propertyReader.getProperty(SERVER_PORT)));

			var context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath(CONTEXT);

			var listAllProductsHolder = new ServletHolder(new ListAllProductsServlet(productService, viewGenerator));
			context.addServlet(listAllProductsHolder, "/");
			context.addServlet(listAllProductsHolder, "/products");
			context.addServlet(new ServletHolder(new CreateProductServlet(productService, viewGenerator)),
					"/products/add");
			context.addServlet(new ServletHolder(new ModifyProductServlet(productService, viewGenerator)),
					"/products/edit/*");
			context.addServlet(new ServletHolder(new DeleteProductServlet(productService, viewGenerator)),
					"/products/delete/*");
			context.addServlet(new ServletHolder(new SaveProductServlet(productService, viewGenerator)),
					"/saveproduct");
			context.addServlet(new ServletHolder(new LoginFormServlet(viewGenerator)), "/loginform");
			context.addServlet(new ServletHolder(new LoginServlet(userService, securityService, viewGenerator)),
					"/login");

			context.addFilter(new FilterHolder(new AuthenticationFilter(securityService)), "/*",
					EnumSet.allOf(DispatcherType.class));

			server.setHandler(context);

			server.start();
			server.join();

		} catch (Exception e) {
			log.error("general type exception");
			e.printStackTrace();
		}

	}

}
