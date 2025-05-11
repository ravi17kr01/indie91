package com.example.indie91.Repositories;

import com.example.indie91.Models.InfluencerProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluencerProfileRepository extends MongoRepository<InfluencerProfile, String> {
    // Custom queries can be added here if needed
}
