package com.example.indie91.DTO;

import java.util.UUID;

public interface UserFullProfileProjection {
    UUID getUserId();
    String getUserName();
    String getEmail();
    String getPhone();
    String getAvatarUrl();

    UUID getInfluencerProfileId();
    Double getTotalEarnings();
    Double getCurrentEarnings();
    Integer getTotalSales();
    Integer getFollowersCount();
    Double getEngagementRate();
    Integer getBrandCollaborations();

    UUID getContentId();
    String getContentType();
    String getUrl();
    Integer getViewsCount();
    Integer getLikesCount();
    String getContentDescription();

    UUID getTagId();
    String getTagName();

    UUID getProductId();
    String getProductName();
    String getCaption();
    Double getPrice();

    UUID getBrandId();
    String getBrandName();

    UUID getMediaId();
    String getMediaUrl();

    String getProductImageUrl();
    String getImageAltText();
}
