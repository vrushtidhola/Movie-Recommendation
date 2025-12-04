package com.personal.movie_recommendation_web.service;

import com.personal.movie_recommendation_web.model.Friend;
import com.personal.movie_recommendation_web.model.MovieRecommendation;
import com.personal.movie_recommendation_web.repository.FriendRepository;
import com.personal.movie_recommendation_web.repository.MovieRecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final FriendRepository friendRepository;
    private final MovieRecommendationRepository movieRecommendationRepository;

    // Add friend
    public Friend addFriend(Long userId, Long friendId) {
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        return friendRepository.save(friend);
    }

    // Get all friends of a user
    public List<Long> getFriends(Long userId) {
        return friendRepository.findByUserId(userId)
                .stream()
                .map(Friend::getFriendId)
                .toList();
    }

    // Recommend a movie to a specific friend
    public MovieRecommendation recommendMovie(Long userId, Long friendId, String movieId) {
        MovieRecommendation rec = new MovieRecommendation();
        rec.setUserId(userId);
        rec.setFriendId(friendId);
        rec.setMovieId(movieId);
        return movieRecommendationRepository.save(rec);
    }

    // Fetch all movies recommended to the logged-in user
    public List<MovieRecommendation> getMoviesRecommendedToUser(Long userId) {
        return movieRecommendationRepository.findByFriendId(userId);
    }

    // Fetch all movies recommended BY a user (their outgoing recommendations)
    public List<MovieRecommendation> getMoviesRecommendedByUser(Long userId) {
        return movieRecommendationRepository.findByUserId(userId);
    }

    // Check if user is friend
    public boolean isFriend(Long userId, Long otherUserId) {
        return friendRepository.findByUserId(userId)
                .stream()
                .anyMatch(f -> f.getFriendId().equals(otherUserId));
    }
}