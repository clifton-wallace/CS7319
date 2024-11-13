package com.cs7319.finalproject.user_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs7319.finalproject.user_service.models.User;
import com.cs7319.finalproject.user_service.models.UserRole;
import com.cs7319.finalproject.user_service.repositories.UserRepository;

// Service providing user functionality such as:
// Create, retrieve, login, and delete users.
// Includes only the necessary functions for this project, 
// representing a subset of a real application.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create New User
    public User createUser(User user) {
        // Check For Existing User; Email Must Be Unique
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        if (user.getRole().name().equals("ADMIN")) {
            user.setRole(UserRole.ADMIN);
        }
        else {
            user.setRole(UserRole.USER);
        }
            
        return userRepository.save(user);
    }

    // Retrieve User Using Id
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Retrieve User By Email Address (Also Login)
    public User getUserByEmail(String emailAddress) {
        return userRepository.findByEmail(emailAddress);
    }

    // Remove Existing User
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Log User Into System By Validating User Name And Password
    // Return The Id Of The User If Successful
    // Return -1 If The Login Process Fails
    // This Is A Very Basic Implementation And Not Production Worthy
    public User login(String emailAddress, String password) {
        User user = userRepository.findByEmail(emailAddress);

        if (user != null) {
            if (user.getPassword() == null ? password == null : user.getPassword().equals(password)) {
                return user;
            }
        }

        // Null Indicates Login Failed
        return null;
    }
}
