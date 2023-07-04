package com.app.onlineStore.repositories;


import com.app.onlineStore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    public Product findByName (String name); // query to find a user by email
}
