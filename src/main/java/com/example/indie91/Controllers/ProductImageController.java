package com.example.indie91.Controllers;

import com.example.indie91.Models.ProductImage;
import com.example.indie91.POJO.ApiResponse;
import com.example.indie91.Services.ProductImageService;
import com.example.indie91.Utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productImage")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ProductImage>>> getAllImages() {
        try {
            List<ProductImage> images = productImageService.getAllImages();
            if (images.isEmpty()) {
                return ResponseUtils.success(images, "No images found");
            }
            return ResponseUtils.success(images, "Product images fetched successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching product images");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductImage>> getImageById(@PathVariable String id) {
        try {
            Optional<ProductImage> image = productImageService.getImageById(id);
            return image.map(value -> ResponseUtils.success(value, "Image found"))
                    .orElseGet(() -> ResponseUtils.error(HttpStatus.NOT_FOUND, "Image not found"));
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching image");
        }
    }

    @GetMapping("/byProduct/{productId}")
    public ResponseEntity<ApiResponse<List<ProductImage>>> getImagesByProductId(@PathVariable String productId) {
        try {
            List<ProductImage> images = productImageService.getImagesByProductId(productId);
            if (images.isEmpty()) {
                return ResponseUtils.success(images, "No images found for the product");
            }
            return ResponseUtils.success(images, "Images fetched for product successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching product images by product ID");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductImage>> createImage(@RequestBody ProductImage image) {
        try {
            ProductImage saved = productImageService.createImage(image);
            return ResponseUtils.success(saved, "Image created successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating image");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteImage(@PathVariable String id) {
        try {
            productImageService.deleteImage(id);
            return ResponseUtils.success("Image deleted successfully", "Deleted");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting image");
        }
    }
}
