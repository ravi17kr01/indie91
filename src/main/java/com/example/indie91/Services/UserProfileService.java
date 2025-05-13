package com.example.indie91.Services;

import com.example.indie91.DTO.UserFullProfileDTO;
import com.example.indie91.DTO.UserFullProfileProjection;
import com.example.indie91.Repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    //for all users
    public String getAllUsersProfile(){
        return userProfileRepository.getFullUserProfileJson();
    }

    //for single user
    public String getSingleUserProfile(UUID userId){
        return userProfileRepository.getSingleUserProfileJson(userId);
    }
}
