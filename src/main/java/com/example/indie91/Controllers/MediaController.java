package com.example.indie91.Controllers;

import com.example.indie91.Models.Media;
import com.example.indie91.POJO.ApiResponse;
import com.example.indie91.Services.MediaService;
import com.example.indie91.Utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing ProductMedia entities.
 */
@RestController
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService productMediaService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Media>>> getAllMedia() {
        try {
            List<Media> mediaList = productMediaService.getAllMedia();
            if (mediaList.isEmpty()) {
                return ResponseUtils.success(mediaList, "No media found");
            }
            return ResponseUtils.success(mediaList, "Product media fetched successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching product media");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Media>> getMediaById(@PathVariable UUID id) {
        try {
            Optional<Media> media = productMediaService.getMediaById(id);
            return media.map(value -> ResponseUtils.success(value, "Media found"))
                    .orElseGet(() -> ResponseUtils.error(HttpStatus.NOT_FOUND, "Media not found"));
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching media");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Media>> createMedia(@RequestBody Media media) {
        try {
            Media savedMedia = productMediaService.createMedia(media);
            return ResponseUtils.success(savedMedia, "Media created successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating media");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMedia(@PathVariable UUID id) {
        try {
            productMediaService.deleteMedia(id);
            return ResponseUtils.success("Media deleted successfully", "Deleted");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting media");
        }
    }
}
