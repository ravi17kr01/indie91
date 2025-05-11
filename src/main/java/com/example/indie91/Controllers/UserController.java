package com.example.indie91.Controllers;

import com.example.indie91.Models.User;
import com.example.indie91.POJO.ApiResponse;
import com.example.indie91.Services.UserService;
import com.example.indie91.Utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                return ResponseUtils.success(users, "No users found");
            }
            return ResponseUtils.success(users, "Users fetched successfully");
        } catch (Exception e) {
            logger.error("Error fetching users", e); // Log the error with exception stack trace
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching users");
        }
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable String id) {
        try {
            Optional<User> user = userService.getUserById(id);
            return user.map(value -> ResponseUtils.success(value, "User found"))
                    .orElseGet(() -> ResponseUtils.error(HttpStatus.NOT_FOUND, "User not found"));
        } catch (Exception e) {
            logger.error("Error fetching user with ID: {}", id, e); // Log the error with ID and exception
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching user");
        }
    }

    // Create user
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        try {
            User savedUser = userService.createUser(user);
            return ResponseUtils.success(savedUser, "User created successfully");
        } catch (Exception e) {
            logger.error("Error creating user", e); // Log the error with exception stack trace
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user");
        }
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String id) {
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isEmpty()) {
                return ResponseUtils.error(HttpStatus.NOT_FOUND, "User not found");
            }
            userService.deleteUser(id);
            return ResponseUtils.success("User deleted successfully", "Deleted");
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", id, e); // Log the error with ID and exception
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting user");
        }
    }
}
