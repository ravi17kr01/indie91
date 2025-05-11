package com.example.indie91.Repositories;

import com.example.indie91.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    // Example: Find product by slug
    Optional<Product> findBySlug(String slug);

    // Example: Find all active products
    List<Product> findByIsActiveTrue();
}
