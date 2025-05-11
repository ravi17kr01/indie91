package com.example.indie91.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "content")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content {

    @Id
    private String id;

    private String contentType;
    private String url;
    private String description;

    private boolean isActive;
    private Integer viewsCount;
    private Integer likesCount;
    private Long shareCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @DBRef
    private InfluencerProfile influencer;

    @DBRef
    private Tag tag;
}
