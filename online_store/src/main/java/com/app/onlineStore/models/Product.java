package com.app.onlineStore.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "product_tb")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double productPrice; // price for a single product
    private Integer totalQuantity; // totoal product quantity in store
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Cart> carts = new ArrayList<>();

    public Product (String name,
                   Double price,
                   Integer quantity,
                   List<Cart> carts) {
        this.name = name;
        this.productPrice = price;
        this.totalQuantity = quantity;
        this.carts = carts;
    }
    public void removeAllCarts() {
        carts.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Double.compare(product.productPrice, productPrice) == 0 &&
                totalQuantity == product.totalQuantity &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(carts, product.carts);
    }



}
