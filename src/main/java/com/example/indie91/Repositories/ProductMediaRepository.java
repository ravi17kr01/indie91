package com.example.indie91.Repositories;

import com.example.indie91.Models.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductMediaRepository extends JpaRepository<ProductMedia, Long> {
    // Example: Find all product media by productId
    List<ProductMedia> findByProductId(UUID productId);

    // Example: Find a single product media by its mediaId
    Optional<ProductMedia> findByMediaId(UUID mediaId);

    // Example: Find all product media by a list of product IDs
    List<ProductMedia> findByProductIdIn(List<String> productIds);
}
