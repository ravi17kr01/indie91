package com.example.indie91.Repositories;

import com.example.indie91.Models.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ProductMedia entity.
 */
@Repository
public interface MediaRepository extends MongoRepository<Media, String> {
    // Additional queries if needed
}
