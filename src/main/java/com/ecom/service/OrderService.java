package com.ecom.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecom.model.Cart;
import com.ecom.model.CartProduct;
import com.ecom.model.Order;
import com.ecom.model.OrderStatus;
import com.ecom.model.Address;
import com.ecom.repository.AddressRepository;
import com.ecom.repository.CartRepository;
import com.ecom.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	public Order placeOrder(Integer cartId, Integer addressId) {
		Cart cart = cartRepo.findById(cartId)
	            .orElse(null);
	        Address address = addressRepo.findById(addressId)
	            .orElse(null);

	        Order order = new Order();
	        order.setUser(cart.getUser());
	        order.setAddress(address);
	        order.setProducts(cart.getCartProducts().stream()
	        	    .map(CartProduct::getProduct) // Extract only the products
	        	    .collect(Collectors.toList()));
	        order.setTotalPrice(cart.getTotalPrice());
	        order.setOrderDate(new Date());
	        order.setOrderStatus(OrderStatus.PENDING);

	        return orderRepo.save(order);
	}
	public Order getOrderById(Integer orderId) {
		return orderRepo.findById(orderId).orElse(null);
	}
	public List<Order> getOrdersByUserId(Integer userId) {
		return orderRepo.findAll().stream()
	            .filter(order -> order.getUser().getUserId().equals(userId))
	            .collect(Collectors.toList());
	}

}
