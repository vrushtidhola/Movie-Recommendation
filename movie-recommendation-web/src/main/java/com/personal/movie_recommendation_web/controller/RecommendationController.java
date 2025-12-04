package com.personal.movie_recommendation_web.controller;

import lombok.RequiredArgsConstructor;
import com.personal.movie_recommendation_web.service.RecommendationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    // returns list of TMDB ids + optional recommendedBy field
    @GetMapping("/personal/{userId}")
    public List<Map<String,Object>> personal(@PathVariable Long userId){
        return recommendationService.personalRecommendations(userId, 20);
    }

    @GetMapping("/friends/{userId}")
    public List<Map<String,Object>> friends(@PathVariable Long userId){
        return recommendationService.recommendedByFriends(userId, 20);
    }
}
