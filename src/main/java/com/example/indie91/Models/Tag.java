package com.example.indie91.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    private String id;

    private String name;
    private String description;
}
