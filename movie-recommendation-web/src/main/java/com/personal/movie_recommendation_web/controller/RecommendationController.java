package com.personal.movie_recommendation_web.controller;

import lombok.RequiredArgsConstructor;
import com.personal.movie_recommendation_web.service.RecommendationService;
import com.personal.movie_recommendation_web.repository.MovieRecommendationRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import com.personal.movie_recommendation_web.model.MovieRecommendation;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
@CrossOrigin
public class RecommendationController {

    private final MovieRecommendationRepository repo;

    @PostMapping("/add")
    public ResponseEntity<?> recommend(@RequestBody MovieRecommendation req) {
        return ResponseEntity.ok(repo.save(req));
    }

    @GetMapping("/for-user/{userId}")
    public List<MovieRecommendation> getForUser(@PathVariable Long userId) {
        return repo.findByFriendId(userId);
    }
}
