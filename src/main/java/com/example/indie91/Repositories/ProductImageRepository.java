package com.example.indie91.Repositories;

import com.example.indie91.Models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {

    // Find all product images by productId
    List<ProductImage> findByProductId(UUID productId);

    // Find a single product image by its imageId
    Optional<ProductImage> findByImageId(UUID imageId);

    // Find all product images ordered by prioritySeq
    List<ProductImage> findByProductIdOrderByPrioritySeqAsc(UUID productId);

    // Find a single product image by its imageUrl
    Optional<ProductImage> findByImageUrl(String imageUrl);
}
