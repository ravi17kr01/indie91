package com.example.indie91.Services;

import com.example.indie91.Models.Brand;
import com.example.indie91.Repositories.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * BrandService class contains the business logic for managing brands. It interacts
 * with the BrandRepository to perform CRUD operations on the "brand" collection in MongoDB.
 */
@Service
public class BrandService {

    private static final Logger logger = LoggerFactory.getLogger(BrandService.class);

    @Autowired
    private BrandRepository brandRepository;

    /**
     * Retrieves all brands from the repository.
     *
     * @return List of all brands.
     */
    public List<Brand> getAllBrands() {
        try {
            return brandRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching all brands", e);
            throw new RuntimeException("Error fetching all brands", e);
        }
    }

    /**
     * Retrieves a brand by its ID.
     *
     * @param id The ID of the brand to retrieve.
     * @return An Optional containing the brand if found, else an empty Optional.
     */
    public Optional<Brand> getBrandById(String id) {
        try {
            return brandRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error fetching brand with ID: {}", id, e);
            throw new RuntimeException("Error fetching brand", e);
        }
    }

    /**
     * Creates a new brand and saves it to the repository.
     *
     * @param brand The brand to be created.
     * @return The saved brand.
     */
    public Brand createBrand(Brand brand) {
        try {
            return brandRepository.save(brand);
        } catch (Exception e) {
            logger.error("Error creating brand", e);
            throw new RuntimeException("Error creating brand", e);
        }
    }

    /**
     * Deletes a brand by its ID.
     *
     * @param id The ID of the brand to delete.
     */
    public void deleteBrand(String id) {
        try {
            brandRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting brand with ID: {}", id, e);
            throw new RuntimeException("Error deleting brand", e);
        }
    }
}
