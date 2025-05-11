package com.example.indie91.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "influencer_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfluencerProfile {

    @Id
    private String id;

    private BigDecimal totalEarnings;
    private BigDecimal currentEarnings;

    private LocalDateTime lastPaidAt;

    private Integer totalSales;
    private Integer followersCount;
    private Double engagementRate;

    private Integer brandCollaborations;
    private Integer contentCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String userId;  // UUID reference to user
}
