package com.ecom.repository;

import com.ecom.model.Cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
	public List<Cart> findByUser_UserId(Integer userId);
}
