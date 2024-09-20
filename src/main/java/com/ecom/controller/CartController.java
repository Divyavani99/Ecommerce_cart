package com.ecom.controller;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.ecom.model.Cart;
import com.ecom.model.User;
import com.ecom.service.CartService;
import com.ecom.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private UserService userService;
    

    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestParam Integer userId) {
        return ResponseEntity.ok(cartService.createCart(userId));
    }

    @PostMapping("/addProductToCart")
    public String addProductToCart(@RequestParam("cartId") Integer cartId, 
    	@RequestParam("productId") Integer productId,@RequestParam("userId") Integer userId, @RequestParam("quantity") Integer quantity, HttpSession session,
    	Model model) {
    	
    	System.out.println("inside addProductToCart method");
        try {
            cartService.addProductToCart(cartId, productId,userId, quantity,session);
            model.addAttribute("successMessage", "Product added to cart successfully."); 
            } catch (Exception e) { 
            	model.addAttribute("errorMessage", e.getMessage()); 
            	}
        return "redirect:/products/" + productId;
    	
    }

    @GetMapping("/{cartId}")
    public String showCart(@PathVariable Integer cartId, Model model) {
		
		   Cart cart = cartService.getCartById(cartId); 
		   if (cart != null && !cart.getCartProducts().isEmpty()) { 
			    model.addAttribute("cart", cart); 
			    model.addAttribute("productCount", cart.getProductCount());  } 
		   else { 
		  model.addAttribute("message", "Your cart is empty");  } 
		  return "cart";
		     }

    @DeleteMapping("/{cartId}/items/delete")
    public String deleteProductFromCart(@PathVariable Integer cartId, @RequestParam Integer productId,Model model) {
    	 try {
             cartService.removeProductFromCart(cartId, productId);
             model.addAttribute("successMessage", "Product removed from cart.");
         } catch (Exception e) {
             model.addAttribute("errorMessage", e.getMessage());
         }
         return "redirect:/cart/" + cartId;
    }
    
    @PostMapping("/{cartId}/clear")
    public String clearCart(@PathVariable Integer cartId, Model model) {
        try {
            cartService.clearCart(cartId);
            model.addAttribute("successMessage", "Cart cleared successfully.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/cart/" + cartId;
    }
    @PostMapping("/checkout")
    public String checkout(@RequestParam Integer cartId, Model model) {
        try {
            cartService.checkout(cartId);
            model.addAttribute("successMessage", "Checkout successful.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "Thanks for Shopping. visit again!";
    }
    
    
}
