package com.example.indie91.Services;

import com.example.indie91.Models.ContentProduct;
import com.example.indie91.Repositories.ContentProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentProductService {

    private static final Logger logger = LoggerFactory.getLogger(ContentProductService.class);

    @Autowired
    private ContentProductRepository contentProductRepository;

    public List<ContentProduct> getAll() {
        try {
            return contentProductRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching content-product mappings", e);
            throw new RuntimeException("Failed to fetch content-product mappings");
        }
    }

    public Optional<ContentProduct> getById(String id) {
        try {
            return contentProductRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error fetching mapping by id: {}", id, e);
            throw new RuntimeException("Failed to fetch mapping");
        }
    }

    public ContentProduct create(ContentProduct mapping) {
        try {
            return contentProductRepository.save(mapping);
        } catch (Exception e) {
            logger.error("Error creating content-product mapping", e);
            throw new RuntimeException("Failed to create mapping");
        }
    }

    public void delete(String id) {
        try {
            contentProductRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting mapping with id: {}", id, e);
            throw new RuntimeException("Failed to delete mapping");
        }
    }

    public List<ContentProduct> getByContentId(String contentId) {
        try {
            return contentProductRepository.findByContentId(contentId);
        } catch (Exception e) {
            logger.error("Error fetching by contentId: {}", contentId, e);
            throw new RuntimeException("Failed to fetch by contentId");
        }
    }

    public List<ContentProduct> getByProductId(String productId) {
        try {
            return contentProductRepository.findByProductId(productId);
        } catch (Exception e) {
            logger.error("Error fetching by productId: {}", productId, e);
            throw new RuntimeException("Failed to fetch by productId");
        }
    }
}
