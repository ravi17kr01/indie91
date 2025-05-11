package com.example.indie91.Repositories;

import com.example.indie91.Models.ContentProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentProductRepository extends MongoRepository<ContentProduct, String> {
    List<ContentProduct> findByContentId(String contentId);
    List<ContentProduct> findByProductId(String productId);
}
