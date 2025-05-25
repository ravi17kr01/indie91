package com.example.indie91.Services;

import com.example.indie91.Models.Brand;
import com.example.indie91.Models.Product;
import com.example.indie91.Repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Handles business logic for products.
 */
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching all products", e);
            throw new RuntimeException("Error fetching all products", e);
        }
    }

    public Optional<Product> getProductById(UUID id) {
        try {
            return productRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error fetching product with ID: {}", id, e);
            throw new RuntimeException("Error fetching product", e);
        }
    }

    public Product createProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            logger.error("Error creating product", e);
            throw new RuntimeException("Error creating product", e);
        }
    }

    /**
     * Updates an existing product with new data (supports partial updates).
     *
     * @param id           The ID of the brand to update.
     * @param updatedProduct The partial brand object containing updated fields.
     * @return The updated brand.
     */
    public Product updateProduct(UUID id, Product updatedProduct) {
        try {
            Optional<Product> existingProductOpt = productRepository.findById(id);
            if (existingProductOpt.isEmpty()) {
                throw new RuntimeException("Product not found");
            }

            Product existingProduct = existingProductOpt.get();

            // Merge updated fields (only if non-null)
            if (updatedProduct.getName() != null) {
                existingProduct.setName(updatedProduct.getName());
            }

            if(updatedProduct.getLikeCount() != null){
                existingProduct.setLikeCount(updatedProduct.getLikeCount());
            }

            if(updatedProduct.getShareCount() != null){
                existingProduct.setShareCount(updatedProduct.getShareCount());
            }

            // Add more field checks as needed
            return productRepository.save(existingProduct);
        } catch (Exception e) {
            logger.error("Error updating brand with ID: {}", id, e);
            throw new RuntimeException("Error updating brand", e);
        }
    }


    public void deleteProduct(UUID id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting product with ID: {}", id, e);
            throw new RuntimeException("Error deleting product", e);
        }
    }

    public Product incrementLikeCount(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int currentLikes = product.getLikeCount() != null ? product.getLikeCount() : 0;
        product.setLikeCount(currentLikes + 1);
        return productRepository.save(product);
    }

}
