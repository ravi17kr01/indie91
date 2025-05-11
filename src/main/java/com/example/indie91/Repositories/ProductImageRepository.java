package com.example.indie91.Repositories;

import com.example.indie91.Models.ProductImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends MongoRepository<ProductImage, String> {
    // Custom query methods (if needed) can go here
    List<ProductImage> findByProductId(String productId);
}
