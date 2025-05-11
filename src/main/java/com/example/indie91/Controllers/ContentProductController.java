package com.example.indie91.Controllers;

import com.example.indie91.Models.ContentProduct;
import com.example.indie91.POJO.ApiResponse;
import com.example.indie91.Services.ContentProductService;
import com.example.indie91.Utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contentProduct")
public class ContentProductController {

    @Autowired
    private ContentProductService service;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ContentProduct>>> getAll() {
        try {
            List<ContentProduct> list = service.getAll();
            return ResponseUtils.success(list, "Fetched all content-product mappings");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching mappings");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContentProduct>> getById(@PathVariable String id) {
        try {
            Optional<ContentProduct> result = service.getById(id);
            return result.map(value -> ResponseUtils.success(value, "Mapping found"))
                    .orElseGet(() -> ResponseUtils.error(HttpStatus.NOT_FOUND, "Mapping not found"));
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching mapping");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ContentProduct>> create(@RequestBody ContentProduct mapping) {
        try {
            ContentProduct created = service.create(mapping);
            return ResponseUtils.success(created, "Mapping created successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating mapping");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String id) {
        try {
            service.delete(id);
            return ResponseUtils.success("Mapping deleted successfully", "Deleted");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting mapping");
        }
    }

    @GetMapping("/byContent/{contentId}")
    public ResponseEntity<ApiResponse<List<ContentProduct>>> getByContentId(@PathVariable String contentId) {
        try {
            List<ContentProduct> list = service.getByContentId(contentId);
            return ResponseUtils.success(list, "Fetched by content ID");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching by content ID");
        }
    }

    @GetMapping("/byProduct/{productId}")
    public ResponseEntity<ApiResponse<List<ContentProduct>>> getByProductId(@PathVariable String productId) {
        try {
            List<ContentProduct> list = service.getByProductId(productId);
            return ResponseUtils.success(list, "Fetched by product ID");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching by product ID");
        }
    }
}
