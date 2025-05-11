package com.example.indie91.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "content_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentProduct {

    @Id
    private String id;

    private String contentId;
    private String productId;
}
