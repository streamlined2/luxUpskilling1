package org.training.upskilling.onlineshop.controller.product;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.service.dto.ProductDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ProductController {

	private static final String PRODUCTS_ATTRIBUTE = "products";
	private static final String PRODUCT_ATTRIBUTE = "product";

	private final ProductService productService;

	@GetMapping({ "/", "/products" })
	public String getProducts(Model model) {
		model.addAttribute(PRODUCTS_ATTRIBUTE, productService.getAll());
		return "/product-list";
	}

	@GetMapping("/product/add")
	public String addProduct() {
		return "/new-product";
	}

	@PostMapping("/newproduct")
	public String newProduct(@ModelAttribute(PRODUCT_ATTRIBUTE) ProductDto product) {
		productService.add(product);
		return "redirect:/products";
	}

	@GetMapping("/product/edit/{id}")
	public String editProduct(Model model, @PathVariable Long id) {
		Optional<ProductDto> product = productService.findById(id);
		if (product.isPresent()) {
			model.addAttribute(PRODUCT_ATTRIBUTE, product.get());
			return "/modify-product";
		}
		return "/new-product";
	}

	@PostMapping("/saveproduct")
	public String saveProduct(@ModelAttribute(PRODUCT_ATTRIBUTE) ProductDto product) {
		productService.update(product);
		return "redirect:/products";
	}

	@GetMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productService.delete(id);
		return "redirect:/products";
	}

}
