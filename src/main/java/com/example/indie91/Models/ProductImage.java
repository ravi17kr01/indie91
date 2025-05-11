package com.example.indie91.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "product_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id
    private String imageId;

    private String productId;
    private String imageUrl;
    private String altText;
    private Integer prioritySeq;
    private LocalDateTime createdAt;
}
