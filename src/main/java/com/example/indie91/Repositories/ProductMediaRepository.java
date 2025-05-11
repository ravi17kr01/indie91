package com.example.indie91.Repositories;

import com.example.indie91.Models.ProductMedia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMediaRepository extends MongoRepository<ProductMedia, String> {
    List<ProductMedia> findByProductId(String productId);
    List<ProductMedia> findByMediaId(String mediaId);
}
