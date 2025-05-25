package com.example.indie91.Repositories;

import com.example.indie91.Enums.OrderStatus;
import com.example.indie91.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Order findByUserIdAndStatus(UUID userId, OrderStatus status);
}
