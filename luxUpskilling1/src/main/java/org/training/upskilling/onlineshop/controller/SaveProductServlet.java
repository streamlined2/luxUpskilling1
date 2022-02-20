package org.training.upskilling.onlineshop.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.service.dto.ProductDto;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public class SaveProductServlet extends AbstractServlet {

	public SaveProductServlet(ProductService productService, ViewGenerator viewGenerator) {
		super(productService, viewGenerator, true);
	}

	@Override
	public void doWork(HttpServletRequest req) throws ServletException {
		addUpdateProduct(req);
		fetchAllProducts();
	}

	@Override
	public String getDestination() {
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
