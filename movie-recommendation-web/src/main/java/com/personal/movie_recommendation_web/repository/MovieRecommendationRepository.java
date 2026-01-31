package com.personal.movie_recommendation_web.repository;

import com.personal.movie_recommendation_web.model.MovieRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRecommendationRepository extends JpaRepository<MovieRecommendation, Long> {
    List<MovieRecommendation> findByFriendId(Long friendId);
    List<MovieRecommendation> findByUserId(Long userId);
    List<MovieRecommendation> findByFriendIdOrderByCreatedAtDesc(Long userId);

}
