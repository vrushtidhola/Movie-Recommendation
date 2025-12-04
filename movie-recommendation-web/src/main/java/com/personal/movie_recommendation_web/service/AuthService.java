package com.personal.movie_recommendation_web.service;

import com.personal.movie_recommendation_web.dto.JwtResponse;
import com.personal.movie_recommendation_web.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import com.personal.movie_recommendation_web.model.User;
import com.personal.movie_recommendation_web.repository.UserRepository;
import com.personal.movie_recommendation_web.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public ResponseEntity<?> signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");

        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        String token = jwtUtil.generateToken(request.getEmail());
        return ResponseEntity.ok(new JwtResponse(token)); }

    public String login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) throw new RuntimeException("User not found");

        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}