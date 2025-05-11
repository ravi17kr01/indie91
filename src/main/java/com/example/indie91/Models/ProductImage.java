package com.example.indie91.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue
    @Column(name = "image_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID imageId;

    @Column(name = "product_id", columnDefinition = "uuid")
    private UUID productId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "priority_seq")
    private Integer prioritySeq;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
