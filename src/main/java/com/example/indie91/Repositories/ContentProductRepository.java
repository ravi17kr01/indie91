package com.example.indie91.Repositories;

import com.example.indie91.Models.ContentProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface ContentProductRepository extends JpaRepository<ContentProduct, UUID> {

    List<ContentProduct> findByContentId(UUID contentId);

    @Query(value = """
            select distinct c.url from content_products as cp
                        left join content as c
                        on cp.content_id = c.id
                        where cp.product_id = :productId
            """, nativeQuery = true)
    List<String> findByProductId(@Param("productId") UUID productId);
}
