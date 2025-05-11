package com.example.indie91.Services;

import com.example.indie91.Models.User;
import com.example.indie91.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service // Marks this class as a service layer component, making it eligible for Spring's component scanning and dependency injection.
public class UserService {

    // Automatically injects an instance of UserRepository into this service class.
    @Autowired
    private UserRepository userRepository;

    // Fetches and returns all users from the database using the UserRepository.
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Searches the database for a user with the given ID and returns the result as an Optional.
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    // Saves the provided user object into the database and returns the saved instance.
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Deletes the user with the given ID from the database.
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
