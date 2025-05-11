package com.example.indie91.Repositories;

import com.example.indie91.Models.InfluencerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface InfluencerProfileRepository extends JpaRepository<InfluencerProfile, UUID> {

    // Find by userId (uuid)
    Optional<InfluencerProfile> findByUserId(UUID userId);
}
