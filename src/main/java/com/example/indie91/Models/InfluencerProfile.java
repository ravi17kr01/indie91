package com.example.indie91.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "influencer_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfluencerProfile {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "total_earnings")
    private BigDecimal totalEarnings;

    @Column(name = "current_earnings")
    private BigDecimal currentEarnings;

    @Column(name = "last_paid_at")
    private LocalDateTime lastPaidAt;

    @Column(name = "total_sales")
    private Integer totalSales;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "engagement_rate")
    private Double engagementRate;

    @Column(name = "brand_collaborations")
    private Integer brandCollaborations;

    @Column(name = "_content_count")
    private Integer underscoreContentCount;

    @Column(name = "content_count")
    private Integer contentCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Choose one of these based on actual usage:
    @Column(name = "user_id")
    private UUID userId;

    // If you actually use user_id (varchar), then:
    // @Column(name = "user_id")
    // private String userIdString;
}
