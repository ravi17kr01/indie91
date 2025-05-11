package com.example.indie91.Repositories;

import com.example.indie91.Models.Content;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends MongoRepository<Content, String> {
    // Custom queries can be added here if needed
}
