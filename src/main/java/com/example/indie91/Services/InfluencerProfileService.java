package com.example.indie91.Services;

import com.example.indie91.Models.InfluencerProfile;
import com.example.indie91.Repositories.InfluencerProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InfluencerProfileService {

    private static final Logger logger = LoggerFactory.getLogger(InfluencerProfileService.class);

    @Autowired
    private InfluencerProfileRepository influencerProfileRepository;

    // Get all influencer profiles
    public List<InfluencerProfile> getAllProfiles() {
        try {
            return influencerProfileRepository.findAll();
        } catch (Exception e) {
            //logger.error("Error fetching influencer profiles", e);
            throw new RuntimeException("Error fetching influencer profiles", e);
        }
    }

    // Get influencer profile by ID
    public Optional<InfluencerProfile> getProfileById(String id) {
        try {
            return influencerProfileRepository.findById(id);
        } catch (Exception e) {
            //logger.error("Error fetching influencer profile with ID: {}", id, e);
            throw new RuntimeException("Error fetching influencer profile", e);
        }
    }

    // Create a new influencer profile
    public InfluencerProfile createProfile(InfluencerProfile influencerProfile) {
        try {
            return influencerProfileRepository.save(influencerProfile);
        } catch (Exception e) {
            //logger.error("Error creating influencer profile", e);
            throw new RuntimeException("Error creating influencer profile", e);
        }
    }

    // Delete influencer profile by ID
    public void deleteProfile(String id) {
        try {
            influencerProfileRepository.deleteById(id);
        } catch (Exception e) {
            //logger.error("Error deleting influencer profile with ID: {}", id, e);
            throw new RuntimeException("Error deleting influencer profile", e);
        }
    }
}
