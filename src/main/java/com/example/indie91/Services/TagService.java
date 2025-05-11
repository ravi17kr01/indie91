package com.example.indie91.Services;

import com.example.indie91.Models.Tag;
import com.example.indie91.Repositories.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * TagService class contains the business logic for managing tags. It interacts
 * with the TagRepository to perform CRUD operations on the "tag" collection in MongoDB.
 */
@Service
public class TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagService.class);

    @Autowired
    private TagRepository tagRepository;

    /**
     * Retrieves all tags from the repository.
     *
     * @return List of all tags.
     */
    public List<Tag> getAllTags() {
        try {
            return tagRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching all tags", e);
            throw new RuntimeException("Error fetching all tags", e);
        }
    }

    /**
     * Retrieves a tag by its ID.
     *
     * @param id The ID of the tag to retrieve.
     * @return An Optional containing the tag if found, else an empty Optional.
     */
    public Optional<Tag> getTagById(UUID id) {
        try {
            return tagRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error fetching tag with ID: {}", id, e);
            throw new RuntimeException("Error fetching tag", e);
        }
    }

    /**
     * Creates a new tag and saves it to the repository.
     *
     * @param tag The tag to be created.
     * @return The saved tag.
     */
    public Tag createTag(Tag tag) {
        try {
            return tagRepository.save(tag);
        } catch (Exception e) {
            logger.error("Error creating tag", e);
            throw new RuntimeException("Error creating tag", e);
        }
    }

    /**
     * Deletes a tag by its ID.
     *
     * @param id The ID of the tag to delete.
     */
    public void deleteTag(UUID id) {
        try {
            tagRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting tag with ID: {}", id, e);
            throw new RuntimeException("Error deleting tag", e);
        }
    }
}
