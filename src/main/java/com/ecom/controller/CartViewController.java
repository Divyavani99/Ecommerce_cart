package com.ecom.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecom.model.Cart;
import com.ecom.model.User;
import com.ecom.service.CartService;
import com.ecom.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/carts")
public class CartViewController {

	   @Autowired
	    private CartService cartService;
	    
	    @Autowired
	    private UserService userService;
	    
	    @GetMapping("/cart")
	    public String loadCart(Principal p,Model model)
	    {
	        if (p == null) {
	            // Log or handle the null case
	            System.out.println("Principal is null. User is not authenticated.");
	            return "redirect:/login";
	        }
	    	  User user = getLoggedInUserDetails(p); 
	          List<Cart> carts = cartService.getCartsByUser(user.getUserId());
	          model.addAttribute("carts", carts);

	          if (!carts.isEmpty()) {
	        	  Cart activeCart = carts.get(carts.size() - 1); // Get the last cart, or any logic you prefer
	              model.addAttribute("cart", activeCart);
	              Double totalOrderPrice = carts.get(carts.size() - 1).getTotalPrice();
	              model.addAttribute("totalOrderPrice", totalOrderPrice);
	          }
	          else {
	              model.addAttribute("cart", null);
	          }
			return "cart";
	    }
		private User getLoggedInUserDetails(Principal p) {
			String email = p.getName();
			User userDtls = userService.getUserByEmail(email);
			return userDtls;
		}
}
