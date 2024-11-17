package com.cs7319.finalproject.user_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs7319.finalproject.user_service.models.ApiResponse;
import com.cs7319.finalproject.user_service.models.LoginRequest;
import com.cs7319.finalproject.user_service.models.User;
import com.cs7319.finalproject.user_service.services.UserService;

// Controller for accessing user functions such as create, delete, and login.
// This is a simplified implementation with minimal 
// functionality for this project.
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Method Add/Registers A new User
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Long>> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user); 
            return ResponseEntity.ok(
                new ApiResponse<>(true, "User registered successfully", newUser.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An unexpected error occurred. Error: " + e.getMessage(), null));
        }
    }

    // Method Validates A User's User Name / Email Address And Password
    // If Succesful, User Is Logged Into The System
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.login(request.getEmailAddress(), request.getPassword());
    
            if (user != null) {
                return ResponseEntity.ok(
                    new ApiResponse<>(true, "User login successful", user));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse<>(false, "Invalid email or password", null));
            }
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>(false, "An unexpected error occurred. Error: " + e.getMessage(), null));
        }
    }

    // Method Retrieves A User By Id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id).orElse(null);
    
            if (user != null) {
                return ResponseEntity.ok(
                    new ApiResponse<>(true, "User found for id: " + id, user));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, "No user associated with Id: " + id, null));
            }
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>(false, "An unexpected error occurred. Error: " + e.getMessage(), null));
        }
    }

    // Method Retrieves A User By Email Address
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<User>> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
    
            if (user != null) {
                return ResponseEntity.ok(
                    new ApiResponse<>(true, "User found for email: " + email, user));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, "No user found for email: " + email, null));
            }
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>(false, "An unexpected error occurred. Error: " + e.getMessage(), null));
        }
    }

    // Method Deletes A User
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> deleteUser(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id).orElse(null);
            
            if (user != null) {
                userService.deleteUser(id);
                return ResponseEntity.ok(
                    new ApiResponse<>(true, "User deleted with id: " + id, id));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, "No user found for id: " + id, null));
            }
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>(false, "An unexpected error occurred. Error: " + e.getMessage(), null));
        }
    }
}