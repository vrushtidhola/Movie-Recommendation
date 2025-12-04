package com.personal.movie_recommendation_web.repository;

import com.personal.movie_recommendation_web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}

