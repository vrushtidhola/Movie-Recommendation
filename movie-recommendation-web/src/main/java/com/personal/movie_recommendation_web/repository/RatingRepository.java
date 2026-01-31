package com.personal.movie_recommendation_web.repository;

import com.personal.movie_recommendation_web.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUserId(Long userId);
    List<Rating> findByMovieTmdbId(Long tmdbId);
    List<Rating> findByUserIdIn(List<Long> userIds); // friends ratings
    List<Rating> findByUserIdInAndMovieTmdbId(List<Long> userIds, Long movieId);
    Optional<Rating> findByUserIdAndMovieTmdbId(Long userId, Long movieTmdbId);

}
