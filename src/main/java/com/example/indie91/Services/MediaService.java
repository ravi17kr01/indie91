package com.example.indie91.Services;

import com.example.indie91.Models.Media;
import com.example.indie91.Repositories.MediaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for handling product media business logic.
 */
@Service
public class MediaService {

    private static final Logger logger = LoggerFactory.getLogger(MediaService.class);

    @Autowired
    private MediaRepository productMediaRepository;

    public List<Media> getAllMedia() {
        try {
            return productMediaRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching product media list", e);
            throw new RuntimeException("Error fetching product media list", e);
        }
    }

    public Optional<Media> getMediaById(UUID id) {
        try {
            return productMediaRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error fetching product media with ID: {}", id, e);
            throw new RuntimeException("Error fetching product media", e);
        }
    }

    public Media createMedia(Media media) {
        try {
            return productMediaRepository.save(media);
        } catch (Exception e) {
            logger.error("Error creating product media", e);
            throw new RuntimeException("Error creating product media", e);
        }
    }

    public void deleteMedia(UUID id) {
        try {
            productMediaRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting product media with ID: {}", id, e);
            throw new RuntimeException("Error deleting product media", e);
        }
    }
}
