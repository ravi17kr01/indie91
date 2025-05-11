package com.example.indie91.Repositories;

import com.example.indie91.Models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
    // Example query
    Media findByUrl(String url);
}
