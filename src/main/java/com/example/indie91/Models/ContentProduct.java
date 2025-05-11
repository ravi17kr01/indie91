package com.example.indie91.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "content_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentProduct {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "content_id", nullable = false)
    private UUID contentId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;
}
