package com.ecom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer productId;

	    private String productName;
	    private Double productPrice;
	    private String productCategory;
	    private Integer productStock;

	    @ManyToMany(mappedBy = "products")
	    private List<Order> orders;
	    
	    public Integer getProductId() {
	        return productId;
	    }

	    public void setProductId(Integer productId) {
	        this.productId = productId;
	    }

	    public String getProductName() {
	        return productName;
	    }

	    public void setProductName(String productName) {
	        this.productName = productName;
	    }

	    public Double getProductPrice() {
	        return productPrice;
	    }

	    public void setProductPrice(Double productPrice) {
	        this.productPrice = productPrice;
	    }

	    public String getProductCategory() {
	        return productCategory;
	    }

	    public void setProductCategory(String productCategory) {
	        this.productCategory = productCategory;
	    }

	    public Integer getProductStock() {
	        return productStock;
	    }

	    public void setProductStock(Integer productStock) {
	        this.productStock = productStock;
	    }

	    public List<Order> getOrders() {
	        return orders;
	    }

	    public void setOrders(List<Order> orders) {
	        this.orders = orders;
	    }
}
