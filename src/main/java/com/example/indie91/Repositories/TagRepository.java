package com.example.indie91.Repositories;

import com.example.indie91.Models.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * TagRepository interface extends MongoRepository to provide CRUD operations
 * for the Tag model. Spring Data MongoDB automatically implements this interface.
 */
@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

    // You can add custom query methods here if needed, for example:
    // List<Tag> findByName(String name);
}
