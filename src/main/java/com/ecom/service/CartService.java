package com.ecom.service;

import java.util.List;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ecom.model.Cart;
import com.ecom.model.CartProduct;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ProductRepository productRepo;
	public Cart createCart(Integer userId) {
	    User user = userRepo.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found"));
	    Cart cart = new Cart();
	    cart.setUser(user);
	    return cartRepo.save(cart);
	}

	public List<Cart> getCartsByUser(Integer userId) {
		List<Cart> carts = cartRepo.findByUser_UserId(userId);
		
		Double totalOrderPrice = 0.0;
		List<Cart> updateCarts = new ArrayList<>();
		for (Cart c : carts) {
			 Double totalPrice = c.getCartProducts().stream()
		                .mapToDouble(cp -> cp.getProduct().getProductPrice() * cp.getQuantity())
		                .sum();
		            c.setTotalPrice(totalPrice);
		            updateCarts.add(c);
		}

		return updateCarts;
	}
	
	public void addProductToCart(Integer cartId, Integer productId,Integer userId, Integer quantity,HttpSession session) {
		 User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		    // Fetch or create the cart for the user
		    Cart cart = cartRepo.findById(cartId)
		            .orElseGet(() -> {
		                Cart newCart = new Cart();
		                // Associate the cart with the user
		                newCart.setUser(user);
		                return cartRepo.save(newCart);
		            });

		    // Retrieve the product by productId
		    Product product = productRepo.findById(productId)
		            .orElseThrow(() -> new RuntimeException("Product not found"));

		    // Check if the product is already in the cart
		    Optional<CartProduct> existingCartProduct = cart.getCartProducts().stream()
		            .filter(cp -> cp.getProduct().getProductId().equals(productId))
		            .findFirst();

		    if (existingCartProduct.isPresent()) {
		        // Update quantity if the product is already in the cart
		        CartProduct cartProduct = existingCartProduct.get();
		        cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
		    } else {
		        // Create a new CartProduct
		        CartProduct cartProd = new CartProduct(product, quantity);
		        cartProd.setCart(cart);
		        cart.getCartProducts().add(cartProd);
		    }

		    // Recalculate totals
		    cart.calculateTotals();
		    
		    // Save the cart
		    cartRepo.save(cart);
	}

	public Cart getCartById(Integer cartId) {
		return cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
	}

	public void clearCart(Integer cartId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getCartProducts().clear();
        cart.calculateTotals();
        cartRepo.save(cart);
	}
	
	 public Cart removeProductFromCart(Integer cartId, Integer productId) {
	        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

	        // Remove product from cart
	        cart.getCartProducts().removeIf(cp -> cp.getProduct().getProductId().equals(productId));

	        // Recalculate totals
	        cart.calculateTotals();
	        return cartRepo.save(cart);
	    }
	 
	 public void checkout(Integer cartId) {
	        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
	        cart.clear();
	        cartRepo.save(cart);
	    }

		
}
