package com.example.indie91.Services;

import com.example.indie91.Models.ProductImage;
import com.example.indie91.Repositories.ProductImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {

    private static final Logger logger = LoggerFactory.getLogger(ProductImageService.class);

    @Autowired
    private ProductImageRepository productImageRepository;

    public List<ProductImage> getAllImages() {
        try {
            return productImageRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching product images", e);
            throw new RuntimeException("Error fetching product images", e);
        }
    }

    public Optional<ProductImage> getImageById(String id) {
        try {
            return productImageRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error fetching product image by ID: {}", id, e);
            throw new RuntimeException("Error fetching product image", e);
        }
    }

    public List<ProductImage> getImagesByProductId(String productId) {
        try {
            return productImageRepository.findByProductId(productId);
        } catch (Exception e) {
            logger.error("Error fetching images for productId: {}", productId, e);
            throw new RuntimeException("Error fetching images by product ID", e);
        }
    }

    public ProductImage createImage(ProductImage image) {
        try {
            return productImageRepository.save(image);
        } catch (Exception e) {
            logger.error("Error saving product image", e);
            throw new RuntimeException("Error saving product image", e);
        }
    }

    public void deleteImage(String id) {
        try {
            productImageRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting product image with ID: {}", id, e);
            throw new RuntimeException("Error deleting product image", e);
        }
    }
}
