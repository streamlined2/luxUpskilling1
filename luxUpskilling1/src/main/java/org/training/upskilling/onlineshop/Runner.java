package org.training.upskilling.onlineshop;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.training.upskilling.onlineshop.controller.CreateProductServlet;
import org.training.upskilling.onlineshop.controller.DeleteProductServlet;
import org.training.upskilling.onlineshop.controller.ListAllProductsServlet;
import org.training.upskilling.onlineshop.controller.ModifyProductServlet;
import org.training.upskilling.onlineshop.controller.SaveProductServlet;
import org.training.upskilling.onlineshop.dao.jdbc.JdbcConnectionFactory;
import org.training.upskilling.onlineshop.dao.jdbc.ProductJdbcDao;
import org.training.upskilling.onlineshop.propertiesreader.EnvironmentPropertyReader;
import org.training.upskilling.onlineshop.service.DefaultProductService;
import org.training.upskilling.onlineshop.service.dto.ProductMapper;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Runner {

	private static final String CONTEXT = "/onlineshop";
	private static final String SERVER_PORT = "port";
	private static final String URL = "url";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static final String MIN_IDLE = "minIdle";
	private static final String MAX_IDLE = "maxIdle";
	private static final String MAX_OPEN_PREPARED_STATEMENTS = "maxOpenPreparedStatements";

	public static void main(String[] args) {

		try (var propertyReader = new EnvironmentPropertyReader();
				var connectionFactory = new JdbcConnectionFactory(propertyReader.getProperty(URL),
						propertyReader.getProperty(USER), propertyReader.getProperty(PASSWORD),
						propertyReader.getIntegerProperty(MIN_IDLE), propertyReader.getIntegerProperty(MAX_IDLE),
						propertyReader.getIntegerProperty(MAX_OPEN_PREPARED_STATEMENTS))) {

			var viewGenerator = new ViewGenerator();
			var productMapper = new ProductMapper();
			var productService = new DefaultProductService(new ProductJdbcDao(connectionFactory, productMapper),
					productMapper);

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

			server.setHandler(context);

			server.start();
			server.join();

		} catch (Exception e) {
			log.error("general type exception");
			e.printStackTrace();
		}

	}

}
