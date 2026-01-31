package com.personal.movie_recommendation_web.controller;

import lombok.RequiredArgsConstructor;
import com.personal.movie_recommendation_web.service.RecommendMovieService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import com.personal.movie_recommendation_web.model.MovieRecommendation;
import com.personal.movie_recommendation_web.dto.RecommendMovieRequest;
import com.personal.movie_recommendation_web.dto.FriendRecommendationResponse;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendMovieController {

    private final RecommendMovieService recommendMovieService;

    @PostMapping("/recommendMovie")

    public ResponseEntity<String> recommendMovie(
            @RequestBody RecommendMovieRequest request) {

        recommendMovieService.recommendMovie(request);
        return ResponseEntity.ok("Movie recommended and rated successfully");
    }
    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<FriendRecommendationResponse>> getFriendRecommendations(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                recommendMovieService.getFriendRecommendations(userId)
        );
    }


}
