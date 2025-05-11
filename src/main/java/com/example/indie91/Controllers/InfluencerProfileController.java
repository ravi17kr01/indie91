package com.example.indie91.Controllers;

import com.example.indie91.Models.InfluencerProfile;
import com.example.indie91.POJO.ApiResponse;
import com.example.indie91.Services.InfluencerProfileService;
import com.example.indie91.Utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/influencer-profile")
public class InfluencerProfileController {

    private static final Logger logger = LoggerFactory.getLogger(InfluencerProfileController.class);

    @Autowired
    private InfluencerProfileService influencerProfileService;

    // Get all influencer profiles
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<InfluencerProfile>>> getAllProfiles() {
        try {
            List<InfluencerProfile> profiles = influencerProfileService.getAllProfiles();
            if (profiles.isEmpty()) {
                return ResponseUtils.success(profiles, "No influencer profiles found");
            }
            return ResponseUtils.success(profiles, "Influencer profiles fetched successfully");
        } catch (Exception e) {
            logger.error("Error fetching influencer profiles", e);
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching influencer profiles");
        }
    }

    // Get influencer profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InfluencerProfile>> getProfileById(@PathVariable UUID id) {
        try {
            Optional<InfluencerProfile> profile = influencerProfileService.getProfileById(id);
            return profile.map(value -> ResponseUtils.success(value, "Influencer profile found"))
                    .orElseGet(() -> ResponseUtils.error(HttpStatus.NOT_FOUND, "Influencer profile not found"));
        } catch (Exception e) {
            logger.error("Error fetching influencer profile with ID: {}", id, e);
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching influencer profile");
        }
    }

    // Create a new influencer profile
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<InfluencerProfile>> createProfile(@RequestBody InfluencerProfile influencerProfile) {
        try {
            InfluencerProfile savedProfile = influencerProfileService.createProfile(influencerProfile);
            return ResponseUtils.success(savedProfile, "Influencer profile created successfully");
        } catch (Exception e) {
            logger.error("Error creating influencer profile", e);
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating influencer profile");
        }
    }

    // Delete influencer profile by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProfile(@PathVariable UUID id) {
        try {
            influencerProfileService.deleteProfile(id);
            return ResponseUtils.success("Influencer profile deleted successfully", "Deleted");
        } catch (Exception e) {
            logger.error("Error deleting influencer profile with ID: {}", id, e);
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting influencer profile");
        }
    }
}
