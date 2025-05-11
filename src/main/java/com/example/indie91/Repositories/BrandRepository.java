package com.example.indie91.Repositories;


import com.example.indie91.Models.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * BrandRepository interface extends MongoRepository to provide CRUD operations
 * for the Brand model. Spring Data MongoDB automatically implements this interface.
 */

@Repository
public interface BrandRepository extends MongoRepository<Brand, String> {

    // You can add custom query methods here if needed, such as:
    // List<Brand> findByName(String name);
}
