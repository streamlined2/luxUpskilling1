package org.training.upskilling.onlineshop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.training.upskilling.onlineshop.controller.CreateProductServlet;
import org.training.upskilling.onlineshop.controller.DeleteProductServlet;
import org.training.upskilling.onlineshop.controller.ListAllProductsServlet;
import org.training.upskilling.onlineshop.controller.ModifyProductServlet;
import org.training.upskilling.onlineshop.controller.SaveProductServlet;
import org.training.upskilling.onlineshop.dao.jdbc.ProductJdbcDao;
import org.training.upskilling.onlineshop.service.DefaultProductService;
import org.training.upskilling.onlineshop.service.dto.ProductMapper;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Runner {

	private static final String CONTEXT = "/onlineshop";
	private static final String SERVER_PORT = "port";
	private static final File propertiesFile = new File("application.properties");

	public static void main(String[] args) {

		Properties props = new Properties();
		try (Reader reader = new FileReader(propertiesFile)) {

			props.load(reader);

			var server = new Server(Integer.parseInt(props.getProperty(SERVER_PORT)));

			var context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath(CONTEXT);

			var viewGenerator = new ViewGenerator();
			var productMapper = new ProductMapper();
			var productService = new DefaultProductService(new ProductJdbcDao(props, productMapper), productMapper);

			var listAllProductsHolder = new ServletHolder(new ListAllProductsServlet(productService, viewGenerator));
			context.addServlet(listAllProductsHolder, "/");
			context.addServlet(listAllProductsHolder, "/products");
			context.addServlet(new ServletHolder(new DeleteProductServlet(productService, viewGenerator)), "/products/delete");
			context.addServlet(new ServletHolder(new CreateProductServlet(productService, viewGenerator)), "/products/add");
			context.addServlet(new ServletHolder(new ModifyProductServlet(productService, viewGenerator)), "/products/edit");
			context.addServlet(new ServletHolder(new SaveProductServlet(productService, viewGenerator)), "/saveproduct");

			server.setHandler(context);

			server.start();
			server.join();

		} catch (FileNotFoundException e) {
			log.error("properties file not found");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("properties file cannot be read");
			e.printStackTrace();
		} catch (Exception e) {
			log.error("general type exception");
			e.printStackTrace();
		}

	}

}
