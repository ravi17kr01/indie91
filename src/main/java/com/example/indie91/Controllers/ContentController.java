package com.example.indie91.Controllers;

import com.example.indie91.Models.Content;
import com.example.indie91.Models.Product;
import com.example.indie91.POJO.ApiResponse;
import com.example.indie91.Services.ContentService;
import com.example.indie91.Utils.ResponseUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/content")
public class ContentController {

    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private ContentService contentService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Content>>> getAllContent() {
        try {
            List<Content> contents = contentService.getAllContent();
            if (contents.isEmpty()) {
                return ResponseUtils.success(contents, "No content found.");
            }
            return ResponseUtils.success(contents, "Fetched all content successfully.");
        } catch (Exception e) {
            logger.error("Error fetching all content", e);
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch content.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Content>> getContentById(@PathVariable UUID id) {
        try {
            Content content = contentService.getContentById(id)
                    .orElse(null);

            if (content == null) {
                return ResponseUtils.error(HttpStatus.NOT_FOUND, "Content not found with id: " + id);
            }

            return ResponseUtils.success(content, "Content found successfully.");
        } catch (Exception e) {
            logger.error("Error fetching content by ID", e);
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch content by ID.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Content>> createContent(@RequestBody Content content) {
        try {
            Content created = contentService.createContent(content);
            return ResponseUtils.success(created, "Content created successfully.");
        } catch (Exception e) {
            logger.error("Error creating content", e);
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create content.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Content>> updateContent(@PathVariable UUID id, @RequestBody Content partialContent) {
        try {
            Content updatedContent = contentService.updateContent(id, partialContent);
            return ResponseUtils.success(updatedContent, "Content updated successfully");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseUtils.error(HttpStatus.NOT_FOUND, e.getMessage());
            }
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating content");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContent(@PathVariable UUID id) {
        try {
            contentService.deleteContent(id);
            return ResponseUtils.success(null, "Content deleted successfully.");
        } catch (Exception e) {
            logger.error("Error deleting content", e);
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete content.");
        }
    }

    @GetMapping("/{id}/like")
    public ResponseEntity<ApiResponse<Content>> likeContent(@PathVariable UUID id) {
        try {
            Content updatedContent = contentService.incrementLikeCount(id);
            return ResponseUtils.success(updatedContent, "Like added to content");
        } catch (RuntimeException e) {
            return ResponseUtils.error(HttpStatus.NOT_FOUND, "Content not found");
        }
    }
}
