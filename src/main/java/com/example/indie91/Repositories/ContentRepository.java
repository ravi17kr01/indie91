package com.example.indie91.Repositories;

import com.example.indie91.Models.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContentRepository extends JpaRepository<Content, UUID> {

    // Find content by its active status
    List<Content> findByIsActive(Boolean isActive);

    // Find content by influencer UUID
    List<Content> findByInfluencer_Id(UUID influencerId);

    // Find content by content type
    List<Content> findByContentType(String contentType);

    // Count content by views (custom query)
    Long countByViewsCountGreaterThan(Integer viewsCount);
}