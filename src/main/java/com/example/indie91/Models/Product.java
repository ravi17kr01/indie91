package com.example.indie91.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String productId;

    private String name;
    private String caption;
    private Boolean isActive;
    private String brandId;
    private String slug;
    private BigDecimal price;
}
