package com.ecom.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
/*import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;*/
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Entity
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CartProduct> cartProducts = new ArrayList<>();
    
    private Integer totalQuantity;
    private Double totalPrice;
    
    public Cart() {
        this.totalQuantity = 0;
        this.totalPrice = 0.0;
    }
    public int getProductCount() {
        return cartProducts.size();
    }
    public void calculateTotals() {
        totalQuantity = cartProducts.stream().mapToInt(CartProduct::getQuantity).sum();
        totalPrice = cartProducts.stream().mapToDouble(cp -> cp.getProduct().getProductPrice() * cp.getQuantity()).sum();
    }
    public void addProduct(CartProduct cartProduct) {
        this.cartProducts.add(cartProduct);
        calculateTotals();
    }
    public void clear() {
        this.cartProducts.clear();
        calculateTotals();
    }
    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
