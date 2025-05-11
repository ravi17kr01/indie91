package com.example.indie91.Services;

import com.example.indie91.Models.ProductMedia;
import com.example.indie91.Repositories.ProductMediaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductMediaService {

    private static final Logger logger = LoggerFactory.getLogger(ProductMediaService.class);

    @Autowired
    private ProductMediaRepository productMediaRepository;

    public List<ProductMedia> getAll() {
        try {
            return productMediaRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching product-media mappings", e);
            throw new RuntimeException("Failed to fetch mappings");
        }
    }

    public Optional<ProductMedia> getById(Long id) {
        try {
            return productMediaRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error fetching mapping by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch mapping");
        }
    }

    public ProductMedia create(ProductMedia mapping) {
        try {
            return productMediaRepository.save(mapping);
        } catch (Exception e) {
            logger.error("Error creating product-media mapping", e);
            throw new RuntimeException("Failed to create mapping");
        }
    }

    public void delete(Long id) {
        try {
            productMediaRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting mapping with ID: {}", id, e);
            throw new RuntimeException("Failed to delete mapping");
        }
    }

    public List<ProductMedia> getByProductId(UUID productId) {
        try {
            return productMediaRepository.findByProductId(productId);
        } catch (Exception e) {
            logger.error("Error fetching mappings by product ID: {}", productId, e);
            throw new RuntimeException("Failed to fetch by product ID");
        }
    }

    public Optional<ProductMedia> getByMediaId(UUID mediaId) {
        try {
            return productMediaRepository.findByMediaId(mediaId);
        } catch (Exception e) {
            logger.error("Error fetching mappings by media ID: {}", mediaId, e);
            throw new RuntimeException("Failed to fetch by media ID");
        }
    }
}
