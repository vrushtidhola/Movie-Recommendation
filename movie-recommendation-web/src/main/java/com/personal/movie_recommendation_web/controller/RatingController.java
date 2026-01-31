package com.personal.movie_recommendation_web.controller;

import lombok.RequiredArgsConstructor;
import com.personal.movie_recommendation_web.service.RatingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import com.personal.movie_recommendation_web.model.MovieRecommendation;
import org.springframework.http.ResponseEntity;
import com.personal.movie_recommendation_web.model.Rating;
import com.personal.movie_recommendation_web.web.dto.RatingRequest;


@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public Rating rateMovie(@RequestBody RatingRequest request) {

        return ratingService.rateMovie(
                request.getUserId(),                 // ✅ getter
                request.getMovieTmdbId(),
                request.getStars(),
                request.getReview(),                 // ✅ you missed this earlier
                request.getVisibleToFriends()        // ✅ correct Lombok getter
        );
    }
}
