package com.example.indie91.Controllers;

import com.example.indie91.Models.Brand;
import com.example.indie91.POJO.ApiResponse;
import com.example.indie91.Services.BrandService;
import com.example.indie91.Utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * BrandController handles HTTP requests for managing brands. It communicates with the
 * BrandService to perform CRUD operations and returns appropriate responses.
 */
@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * Endpoint to retrieve all brands.
     *
     * @return A ResponseEntity containing the list of brands and a success message.
     */
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Brand>>> getAllBrands() {
        try {
            List<Brand> brands = brandService.getAllBrands();
            if (brands.isEmpty()) {
                return ResponseUtils.success(brands, "No brands found");
            }
            return ResponseUtils.success(brands, "Brands fetched successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching brands");
        }
    }

    /**
     * Endpoint to retrieve a brand by its ID.
     *
     * @param id The ID of the brand to retrieve.
     * @return A ResponseEntity containing the brand or an error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Brand>> getBrandById(@PathVariable UUID id) {
        try {
            Optional<Brand> brand = brandService.getBrandById(id);
            return brand.map(value -> ResponseUtils.success(value, "Brand found"))
                    .orElseGet(() -> ResponseUtils.error(HttpStatus.NOT_FOUND, "Brand not found"));
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching brand");
        }
    }

    /**
     * Endpoint to create a new brand.
     *
     * @param brand The brand data to be created.
     * @return A ResponseEntity containing the created brand and a success message.
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Brand>> createBrand(@RequestBody Brand brand) {
        try {
            Brand savedBrand = brandService.createBrand(brand);
            return ResponseUtils.success(savedBrand, "Brand created successfully");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating brand");
        }
    }

    /**
     * Endpoint to delete a brand by its ID.
     *
     * @param id The ID of the brand to delete.
     * @return A ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBrand(@PathVariable UUID id) {
        try {
            brandService.deleteBrand(id);
            return ResponseUtils.success("Brand deleted successfully", "Deleted");
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting brand");
        }
    }
}
