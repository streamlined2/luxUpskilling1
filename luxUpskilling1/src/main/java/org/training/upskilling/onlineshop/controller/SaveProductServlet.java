package org.training.upskilling.onlineshop.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.eclipse.jetty.http.HttpMethod;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.service.dto.ProductDto;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SaveProductServlet extends ProductServlet {

	public SaveProductServlet(ProductService productService, ViewGenerator viewGenerator) {
		super(productService, viewGenerator, true);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, HttpMethod.POST);
	}

	@Override
	public boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		addUpdateProduct(req);
		fetchAllProducts();
		return true;
	}

	@Override
	public String getDestination(HttpServletRequest req, boolean success) {
		return "/products";
	}

	private void addUpdateProduct(HttpServletRequest req) throws ServletException {
		Boolean createFlag = (Boolean) getTemplateVariable(CREATE_PRODUCT_FLAG_ATTRIBUTE,
				"no create product attribute found");

		Long productId;
		if (createFlag.booleanValue()) {
			productId = 0L;
		} else {
			productId = getProductIdFromRequest(req);
		}
		ProductDto product = makeProductFromRequestParameters(req, productId);

		if (createFlag.booleanValue()) {
			productService.add(product);
		} else {
			productService.update(product);
		}

	}

	private ProductDto makeProductFromRequestParameters(HttpServletRequest req, Long id) throws ServletException {
		return new ProductDto(id, getRequestParameter(req, PRODUCT_NAME_PARAMETER, "missing product name parameter"),
				new BigDecimal(getRequestParameter(req, PRODUCT_PRICE_PARAMETER, "missing product price parameter")),
				LocalDate.parse(getRequestParameter(req, PRODUCT_CREATION_DATE_PARAMETER,
						"missing product creation date parameter"), DateTimeFormatter.ISO_LOCAL_DATE));
	}

}
