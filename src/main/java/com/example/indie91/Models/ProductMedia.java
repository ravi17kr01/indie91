package com.example.indie91.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "product_product_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // This is fine as `int8` maps to `Long`

    @Column(name = "product_id", columnDefinition = "uuid")
    private UUID productId;

    @Column(name = "product_media_id", columnDefinition = "uuid")
    private UUID mediaId;
}
