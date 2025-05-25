package com.example.indie91.Repositories;

import com.example.indie91.Models.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemsRepository extends JpaRepository<CartItems, UUID> {
    Optional<CartItems> findByUserIdAndProduct_ProductIdAndOrderId(UUID userId, UUID productId, UUID orderId);

}
