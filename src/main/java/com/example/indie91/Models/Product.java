package com.example.indie91.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID productId;

    private String name;

    private String caption;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "brand_id", columnDefinition = "uuid")
    private UUID brandId;

    private String slug;

    private BigDecimal price;
}
