package com.personal.movie_recommendation_web.service;

import com.personal.movie_recommendation_web.dto.FriendRecommendationResponse;
import com.personal.movie_recommendation_web.dto.ImplicitFriendRecommendation;
import com.personal.movie_recommendation_web.dto.RecommendMovieRequest;
import com.personal.movie_recommendation_web.model.MovieRecommendation;
import com.personal.movie_recommendation_web.model.Rating;
import com.personal.movie_recommendation_web.model.User;
import com.personal.movie_recommendation_web.repository.MovieRecommendationRepository;
import com.personal.movie_recommendation_web.repository.RatingRepository;
import com.personal.movie_recommendation_web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendMovieService {

    private final MovieRecommendationRepository movieRecommendationRepository;
    private final RatingRepository ratingRepository;
    private final SocialService socialService;
    private final UserRepository userRepository;
    private final RatingService ratingService; // ❗ MISSING BEFORE

    // =========================
    // SAVE RECOMMENDATION
    // =========================
    @Transactional
    public void recommendMovie(RecommendMovieRequest req) {

        // 1️⃣ Save explicit recommendation
        MovieRecommendation recommendation = new MovieRecommendation();
        recommendation.setUserId(req.getUserId());
        recommendation.setFriendId(req.getFriendId());
        recommendation.setMovieTmdbId(req.getMovieTmdbId());
        recommendation.setMovieTitle(req.getMovieTitle());
        recommendation.setRating(req.getRating());
        recommendation.setComment(req.getComment());
        recommendation.setCreatedAt(System.currentTimeMillis());

        movieRecommendationRepository.save(recommendation); // ❗ fixed name

        // 2️⃣ Save rating (implicit recommendation source)
        if (req.getRating() != null) {
            ratingService.rateMovie(
                    req.getUserId(),
                    req.getMovieTmdbId(),
                    req.getRating(),
                    req.getComment(),
                    req.getVisibleToFriends()
            );
        }
    }

    // =========================
    // FINAL API METHOD
    // =========================
    public List<FriendRecommendationResponse> getFriendRecommendations(Long userId) {

        List<FriendRecommendationResponse> result = new ArrayList<>();

        // 1️⃣ Explicit recommendations
        result.addAll(getExplicitRecommendations(userId));

        // 2️⃣ Implicit recommendations
        List<ImplicitFriendRecommendation> implicit =
                getImplicitRecommendations(userId, 10);

        for (ImplicitFriendRecommendation imp : implicit) {
            result.add(
                    FriendRecommendationResponse.builder()
                            .source("IMPLICIT")
                            .movieId(imp.getMovieId())
                            .recommendedByCount(imp.getRecommendedByCount())
                            .build()
            );
        }

        return result;
    }

    // =========================
    // EXPLICIT RECOMMENDATIONS
    // =========================
    private List<FriendRecommendationResponse> getExplicitRecommendations(Long userId) {

        return movieRecommendationRepository
                .findByFriendIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(rec -> FriendRecommendationResponse.builder()
                        .source("EXPLICIT")
                        .movieTmdbId(rec.getMovieTmdbId()) // ✅ CRITICAL
                        .movieTitle(rec.getMovieTitle())
                        .rating(rec.getRating())
                        .comment(rec.getComment())
                        .createdAt(rec.getCreatedAt())
                        .recommendedBy(
                                userRepository.findById(rec.getUserId())
                                        .map(User::getUsername)
                                        .orElse("Unknown")
                        )
                        .build()
                )
                .toList();
    }

    // =========================
    // IMPLICIT (RATINGS-BASED)
    // =========================
    private List<ImplicitFriendRecommendation> getImplicitRecommendations(
            Long userId, int limit) {

        List<Long> friendIds = socialService.getFriendIds(userId);
        if (friendIds.isEmpty()) return Collections.emptyList();

        List<Rating> friendRatings =
                ratingRepository.findByUserIdIn(friendIds);

        Map<Long, List<Long>> movieToFriends = new HashMap<>();

        for (Rating r : friendRatings) {
            if (r.getStars() >= 4 && Boolean.TRUE.equals(r.getVisibleToFriends())) {
                movieToFriends
                        .computeIfAbsent(r.getMovieTmdbId(), k -> new ArrayList<>())
                        .add(r.getUserId());
            }
        }

        return movieToFriends.entrySet().stream()
                .sorted((a, b) -> b.getValue().size() - a.getValue().size())
                .limit(limit)
                .map(e -> new ImplicitFriendRecommendation(
                        e.getKey(),
                        e.getValue().size(),
                        e.getValue()
                ))
                .toList();
    }
}
