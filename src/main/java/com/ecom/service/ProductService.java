package com.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	public ProductRepository productRepo;
	public Product addProduct(Product product) {
		return productRepo.save(product);
	}

	public Product updateProduct(Integer productId, Product product) {
		 Product existingProduct = productRepo.findById(productId)
		            .orElse(null);
		        existingProduct.setProductName(product.getProductName());
		        existingProduct.setProductPrice(product.getProductPrice());
		        existingProduct.setProductCategory(product.getProductCategory());
		        existingProduct.setProductStock(product.getProductStock());
		        return productRepo.save(existingProduct);
	}

	public void deleteProduct(Integer productId) {
		productRepo.deleteById(productId);
	}

	public Product getProductById(Integer productId) {
		return productRepo.findById(productId).orElse(null);
	}

	public List<Product> getAllProducts() {
		Iterable<Product> findAll=productRepo.findAll();
		return (List<Product>)findAll;
	}

}
