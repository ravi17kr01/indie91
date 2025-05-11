package com.example.indie91.Repositories;

import com.example.indie91.Models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB repository for Product entity.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    // Additional query methods can be defined here if needed
}
