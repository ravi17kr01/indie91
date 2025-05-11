package com.example.indie91.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "brand")
@Data //generates getters, setters, toString(), equals(), and hashCode()
@NoArgsConstructor //generates a no-arg constructor
@AllArgsConstructor //generates a constructor with all fields
public class Brand {

    @Id
    private String id;

    private String name;
    private String email;
    private String gstinNumber;
    private String panNumber;
    private String description;
    private String logoUrl;
    private String brandSlug;
    private Integer maxDeliveryDay;
    private Integer minDeliveryDay;
}
