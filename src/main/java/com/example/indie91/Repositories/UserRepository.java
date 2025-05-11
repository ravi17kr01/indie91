package com.example.indie91.Repositories;

import com.example.indie91.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Custom queries can be added here if needed
}
