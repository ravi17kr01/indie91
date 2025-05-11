package com.example.indie91.Controllers;

import com.example.indie91.Models.Tag;
import com.example.indie91.POJO.ApiResponse;
import com.example.indie91.Services.TagService;
import com.example.indie91.Utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * TagController handles HTTP requests for managing tags. It communicates with the
 * TagService to perform CRUD operations and returns appropriate responses.
 */
@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * Endpoint to retrieve all tags.
     *
     * @return A ResponseEntity containing the list of tags and a success message.
     */
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Tag>>> getAllTags() {
        try {
            List<Tag> tags = tagService.getAllTags();
            if (tags.isEmpty()) {
                return ResponseUtils.success(tags, "No tags found");
            }
            return ResponseUtils.success(tags, "Tags fetched successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching tags");
        }
    }

    /**
     * Endpoint to retrieve a tag by its ID.
     *
     * @param id The ID of the tag to retrieve.
     * @return A ResponseEntity containing the tag or an error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Tag>> getTagById(@PathVariable String id) {
        try {
            Optional<Tag> tag = tagService.getTagById(id);
            return tag.map(value -> ResponseUtils.success(value, "Tag found"))
                    .orElseGet(() -> ResponseUtils.error(HttpStatus.NOT_FOUND, "Tag not found"));
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching tag");
        }
    }

    /**
     * Endpoint to create a new tag.
     *
     * @param tag The tag data to be created.
     * @return A ResponseEntity containing the created tag and a success message.
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Tag>> createTag(@RequestBody Tag tag) {
        try {
            Tag savedTag = tagService.createTag(tag);
            return ResponseUtils.success(savedTag, "Tag created successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating tag");
        }
    }

    /**
     * Endpoint to delete a tag by its ID.
     *
     * @param id The ID of the tag to delete.
     * @return A ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTag(@PathVariable String id) {
        try {
            tagService.deleteTag(id);
            return ResponseUtils.success("Tag deleted successfully", "Deleted");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting tag");
        }
    }
}
