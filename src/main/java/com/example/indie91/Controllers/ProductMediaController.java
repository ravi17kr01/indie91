package com.example.indie91.Controllers;

import com.example.indie91.Models.ProductMedia;
import com.example.indie91.POJO.ApiResponse;
import com.example.indie91.Services.ProductMediaService;
import com.example.indie91.Utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/productMedia")
public class ProductMediaController {

    @Autowired
    private ProductMediaService service;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ProductMedia>>> getAll() {
        try {
            return ResponseUtils.success(service.getAll(), "Fetched all product-media mappings");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching mappings");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductMedia>> getById(@PathVariable Long id) {
        try {
            Optional<ProductMedia> result = service.getById(id);
            return result.map(r -> ResponseUtils.success(r, "Mapping found"))
                    .orElseGet(() -> ResponseUtils.error(HttpStatus.NOT_FOUND, "Mapping not found"));
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching mapping");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductMedia>> create(@RequestBody ProductMedia mapping) {
        try {
            return ResponseUtils.success(service.create(mapping), "Mapping created successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating mapping");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseUtils.success("Mapping deleted successfully", "Deleted");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting mapping");
        }
    }

    @GetMapping("/byProduct/{productId}")
    public ResponseEntity<ApiResponse<List<ProductMedia>>> getByProductId(@PathVariable UUID productId) {
        try {
            return ResponseUtils.success(service.getByProductId(productId), "Fetched by product ID");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching by product ID");
        }
    }

    @GetMapping("/byMedia/{mediaId}")
    public ResponseEntity<ApiResponse<Optional<ProductMedia>>> getByMediaId(@PathVariable UUID mediaId) {
        try {
            return ResponseUtils.success(service.getByMediaId(mediaId), "Fetched by media ID");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching by media ID");
        }
    }
}
