package com.example.indie91.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullProfileDTO {
    private UUID userId;
    private String userName;
    private String email;
    private String phone;
    private String avatarUrl;

    private UUID influencerProfileId;
    private Double totalEarnings;
    private Double currentEarnings;
    private Integer totalSales;
    private Integer followersCount;
    private Double engagementRate;
    private Integer brandCollaborations;

    private UUID contentId;
    private String contentType;
    private String contentUrl;
    private Integer viewsCount;
    private Integer likesCount;
    private String contentDescription;

    private UUID tagId;
    private String tagName;

    private UUID productId;
    private String productName;
    private String caption;
    private Double price;

    private UUID brandId;
    private String brandName;

    private UUID mediaId;
    private String mediaUrl;

    private String productImageUrl;
    private String imageAltText;
}
