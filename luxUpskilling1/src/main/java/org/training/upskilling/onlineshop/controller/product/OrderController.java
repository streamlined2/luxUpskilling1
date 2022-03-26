package org.training.upskilling.onlineshop.controller.product;

import static org.training.upskilling.onlineshop.Utilities.getTokenCookieValue;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.training.upskilling.onlineshop.security.service.SecurityService;
import org.training.upskilling.onlineshop.service.OrderService;
import org.training.upskilling.onlineshop.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product/cart")
public class OrderController {

	private static final String CART_ATTRIBUTE = "cart";

	private final OrderService orderService;
	private final SecurityService securityService;

	@GetMapping("/add/{id}")
	public String addToOrder(@PathVariable Long id, HttpServletRequest req, HttpSession session) {
		Optional<UserDto> user = securityService.getUser(getTokenCookieValue(req.getCookies()));
		if (user.isPresent()) {
			orderService.orderProduct(user.get(), id, 1);
			session.setAttribute(CART_ATTRIBUTE, orderService.getActiveOrderRepresentation(user.get()));
		}
		return "redirect:/products";
	}

	@GetMapping("/delete/{id}")
	public String deleteFromOrder(@PathVariable Long id, HttpServletRequest req, HttpSession session) {
		Optional<UserDto> user = securityService.getUser(getTokenCookieValue(req.getCookies()));
		if (user.isPresent()) {
			orderService.declineProduct(user.get(), id, 1);
			session.setAttribute(CART_ATTRIBUTE, orderService.getActiveOrderRepresentation(user.get()));
		}
		return "redirect:/products";
	}

}
