package com.example.indie91.Controllers;

import com.example.indie91.Models.Brand;
import com.example.indie91.Models.Product;
import com.example.indie91.POJO.ApiResponse;
import com.example.indie91.Services.ProductService;
import com.example.indie91.Utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Product entities.
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                return ResponseUtils.success(products, "No products found");
            }
            return ResponseUtils.success(products, "Products fetched successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching products");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable UUID id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            return product.map(value -> ResponseUtils.success(value, "Product found"))
                    .orElseGet(() -> ResponseUtils.error(HttpStatus.NOT_FOUND, "Product not found"));
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching product");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.createProduct(product);
            return ResponseUtils.success(savedProduct, "Product created successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating product");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable UUID id, @RequestBody Product partialProduct) {
        try {
            Product updatedProduct = productService.updateProduct(id, partialProduct);
            return ResponseUtils.success(updatedProduct, "Product updated successfully");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseUtils.error(HttpStatus.NOT_FOUND, e.getMessage());
            }
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating product");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
            return ResponseUtils.success("Product deleted successfully", "Deleted");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting product");
        }
    }

    @GetMapping("/{id}/like")
    public ResponseEntity<ApiResponse<Product>> likeProduct(@PathVariable UUID id) {
        try {
            Product updatedProduct = productService.incrementLikeCount(id);
            return ResponseUtils.success(updatedProduct, "Like added to product");
        } catch (RuntimeException e) {
            return ResponseUtils.error(HttpStatus.NOT_FOUND, "Product not found");
        }
    }
}
