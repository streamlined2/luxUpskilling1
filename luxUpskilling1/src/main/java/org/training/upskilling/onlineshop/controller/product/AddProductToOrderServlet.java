package org.training.upskilling.onlineshop.controller.product;

import java.util.Optional;

import org.training.upskilling.onlineshop.controller.Utilities;
import org.training.upskilling.onlineshop.service.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddProductToOrderServlet extends OrderServlet {

	@Override
	public boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		Optional<UserDto> user = securityService.getUser(Utilities.getTokenCookieValue(req));
		if(user.isPresent()) {
			orderService.orderProduct(user.get(), getProductIdFromPath(req), 1);
			updateCartAttribute(user.get());
			return true;
		}
		return false;
	}

	@Override
	public String getDestination(HttpServletRequest req, boolean success) {
		return HOME_URL;
	}

}
