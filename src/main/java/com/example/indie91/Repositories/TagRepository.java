package com.example.indie91.Repositories;

import com.example.indie91.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}
