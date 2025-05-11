package com.example.indie91.Repositories;

import com.example.indie91.Models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {

    // Find brand by name
    List<Brand> findByName(String name);

    // Find brand by email
    List<Brand> findByEmail(String email);

    // Find brand by PAN number
    List<Brand> findByPanNumber(String panNumber);

    // Find brand by brand slug
    List<Brand> findByBrandSlug(String brandSlug);
}
