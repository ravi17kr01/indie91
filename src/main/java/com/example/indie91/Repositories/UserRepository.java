package com.example.indie91.Repositories;

import com.example.indie91.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // Custom queries can be added here if needed
}
