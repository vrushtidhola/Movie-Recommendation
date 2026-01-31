package com.personal.movie_recommendation_web.service;

import com.personal.movie_recommendation_web.model.Rating;
import com.personal.movie_recommendation_web.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    public Rating rateMovie(Long userId,
                            Long movieTmdbId,
                            Integer stars,
                            String comment,
                            Boolean visibleToFriends) {

        Rating rating = ratingRepository
                .findByUserIdAndMovieTmdbId(userId, movieTmdbId)
                .orElseGet(() -> {
                    Rating r = new Rating();
                    r.setUserId(userId);
                    r.setMovieTmdbId(movieTmdbId);
                    r.setCreatedAt(System.currentTimeMillis());
                    return r;
                });

        rating.setStars(stars);
        rating.setReview(comment);
        rating.setVisibleToFriends(visibleToFriends);

        return ratingRepository.save(rating);
    }
}
