package com.personal.movie_recommendation_web.controller;

import com.personal.movie_recommendation_web.dto.SignupRequest;
import com.personal.movie_recommendation_web.model.User;
import com.personal.movie_recommendation_web.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest user) {
        System.out.println("Signup endpoint hit");
        return ResponseEntity.ok(authService.signup(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String token = authService.login(request.get("email"), request.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }
}