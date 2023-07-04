package com.app.onlineStore.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cart_tb")

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_price")
    private Double totalPrice;  // total price for all products inside the cart

    @ManyToMany (cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "cart_product_tb",
            joinColumns = @JoinColumn (name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products ;
    private  List<Integer> productsQuantities; //a list of product quantities in cart
    public Cart(User user, List<Product> products,Double totalPrice,List<Integer> productsQuantities) {
        this.user = user;
        this.products = products;
        this.totalPrice = totalPrice;
        this.productsQuantities = productsQuantities;
    }

    public void addProductsQuantities (Integer quantity){
        this.productsQuantities.add(quantity);
    }


    public void removeProductsQuantitiesByIndex(Integer index){
        this.productsQuantities.remove(index);
    }

    public void removeAllProductsQuantities (){
        this.productsQuantities.clear();
    }


    public void addProduct (Product product){
        this.products.add(product);
    }
    public void removeProduct (Product product){
        this.products.remove(product);
    }
    public void removeAllProducts (){
        this.products.clear();
    }

}
