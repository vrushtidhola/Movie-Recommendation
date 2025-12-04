package com.personal.movie_recommendation_web.service;

import lombok.RequiredArgsConstructor;
import com.personal.movie_recommendation_web.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Starter algorithms:
 * - personalRecommendations: based on genres of top-rated movies by user using TMDB "similar" or discover endpoints.
 * - recommendedByFriends: pick movies highly rated by user's friends.
 *
 * Note: this returns a list of maps {tmdbId:..., score:..., recommendedBy: [friend names...]}.
 */
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RatingRepository ratingRepository;
    private final TmdbService tmdbService;
    private final SocialService socialService; // hypothetical service to get friend ids / names

    public List<Map<String,Object>> personalRecommendations(Long userId, int limit){
        // 1. get user's top-rated movies
        List<com.personal.movie_recommendation_web.model.Rating> ratings = ratingRepository.findByUserId(userId);
        List<Long> topMovies = ratings.stream()
                .filter(r -> r.getStars() >= 4)
                .map(r -> r.getMovieTmdbId()).distinct().collect(Collectors.toList());

        // 2. for each top movie call TMDB similar; collect candidates with simple scoring
        Map<Long, Double> scoreMap = new HashMap<>();
        for(Long tmdbId : topMovies){
            Map<String,Object> similar = tmdbService.getDetails(tmdbId); // for starter; better: call /movie/{id}/similar via TmdbService
            // naive: call discover by genre instead - here we skip details and just sample
            // For brevity, assume tmdbService can fetch similar list:
            Map<String,Object> similarList = tmdbService.getDetails(tmdbId); // replace with tmdbService.getSimilar(tmdbId)
            // parse similarList to gather ids -> increment score
            // (Left as exercise: parse and populate scoreMap)
        }

        // fallback: return trending if nothing else
        Map trending = tmdbService.getTrending("week");
        List<Map<String,Object>> results = (List<Map<String,Object>>) trending.get("results");
        return results.stream().limit(limit).map(m -> Map.of(
                "tmdbId", m.get("id"),
                "title", m.get("title"),
                "thumbnail", "https://image.tmdb.org/t/p/w500" + m.get("poster_path")
        )).collect(Collectors.toList());
    }

    public List<Map<String,Object>> recommendedByFriends(Long userId, int limit){
        // 1. get friend user ids from social service
        List<Long> friendIds = socialService.getFriends(userId); // implement socialService
        if(friendIds.isEmpty()) return Collections.emptyList();

        // 2. get ratings from friends, filter top rated
        List<com.personal.movie_recommendation_web.model.Rating> friendRatings = ratingRepository.findByUserIdIn(friendIds);
        Map<Long, List<Long>> movieToFriends = new HashMap<>();
        for(var r: friendRatings){
            if(r.getStars() >=4 && Boolean.TRUE.equals(r.getVisibleToFriends())){
                movieToFriends.computeIfAbsent(r.getMovieTmdbId(), k-> new ArrayList<>()).add(r.getUserId());
            }
        }

        // 3. rank by count of friends
        return movieToFriends.entrySet().stream()
                .sorted((a,b)-> Integer.compare(b.getValue().size(), a.getValue().size()))
                .limit(limit)
                .map(e-> Map.of("tmdbId", e.getKey(), "recommendedByCount", e.getValue().size(), "recommendedBy", e.getValue()))
                .collect(Collectors.toList());
    }
}
