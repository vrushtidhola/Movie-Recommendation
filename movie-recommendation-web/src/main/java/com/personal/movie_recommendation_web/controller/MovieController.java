package com.personal.movie_recommendation_web.controller;

import com.personal.movie_recommendation_web.service.TmdbService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final TmdbService tmdbService;
    public MovieController(TmdbService tmdbService){ this.tmdbService = tmdbService; }

    @GetMapping("/trending")
    public ResponseEntity<?> getTrending(@RequestParam(defaultValue = "week") String time_window) {
        return ResponseEntity.ok(tmdbService.getTrending(time_window));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String q, @RequestParam(defaultValue="1") int page){
        return ResponseEntity.ok(tmdbService.searchMovie(q, page));
    }

    @GetMapping("/{tmdbId}")
    public ResponseEntity<?> getMovieDetails(@PathVariable Long tmdbId){
        return ResponseEntity.ok(tmdbService.getDetails(tmdbId));
    }
}
