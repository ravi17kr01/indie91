package com.example.indie91.Controllers;

import com.example.indie91.DTO.UserFullProfileDTO;
import com.example.indie91.Exceptions.NoUsersFoundException;
import com.example.indie91.Services.UserProfileService;
import com.example.indie91.Utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/userProfile")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<String> getUserProfile(@RequestParam(required = false) UUID userId ){
        try{
            String json;

            if(userId != null){
                json = userProfileService.getSingleUserProfile(userId);
            }else{
                json = userProfileService.getAllUsersProfile();
            }
            return ResponseEntity.ok(json);
        }catch (Exception e){
            System.out.println("Error while fetching user profiles ----> " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
}
