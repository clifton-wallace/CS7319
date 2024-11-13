package com.cs7319.finalproject.user_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cs7319.finalproject.user_service.models.User;

// Repository For Database Access Using Hibernate
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query to find a user by email
    User findByEmail(String email);
}
