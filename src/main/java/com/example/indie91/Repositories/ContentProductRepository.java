package com.example.indie91.Repositories;

import com.example.indie91.Models.ContentProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface ContentProductRepository extends JpaRepository<ContentProduct, UUID> {

    List<ContentProduct> findByContentId(UUID contentId);

    List<ContentProduct> findByProductId(UUID productId);
}
