package com.personal.movie_recommendation_web.service;

import com.personal.movie_recommendation_web.model.Friend;
import com.personal.movie_recommendation_web.model.MovieRecommendation;
import com.personal.movie_recommendation_web.model.User;
import com.personal.movie_recommendation_web.repository.FriendRepository;
import com.personal.movie_recommendation_web.repository.MovieRecommendationRepository;
import com.personal.movie_recommendation_web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final FriendRepository friendRepository;
    private final MovieRecommendationRepository movieRecommendationRepository;
    private final UserRepository userRepository;

    // ✅ Add friend using EMAIL
    public Friend addFriend(Long userId, String friendEmail) {

        User friend = userRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new RuntimeException("Friend not found"));

        if (friendRepository.existsByUserIdAndFriendId(userId, friend.getId())) {
            throw new RuntimeException("Already friends");
        }

        Friend newFriend = new Friend(userId, friend.getId());
        return friendRepository.save(newFriend);
    }

    // ✅ Used by UI (returns safe data)
    public List<Map<String, Object>> getFriends(Long userId) {

        List<Long> friendIds = friendRepository.findByUserId(userId)
                .stream()
                .map(Friend::getFriendId)
                .toList();

        if (friendIds.isEmpty()) return Collections.emptyList();

        return userRepository.findAllById(friendIds)
                .stream()
                .map(u -> Map.<String, Object>of(
                        "id", u.getId(),
                        "username", u.getUsername(),
                        "email", u.getEmail()
                ))
                .toList();
    }

    // ✅ Used by RecommendationService (logic only)
    public List<Long> getFriendIds(Long userId) {
        return friendRepository.findByUserId(userId)
                .stream()
                .map(Friend::getFriendId)
                .toList();
    }

    // ✅ Recommend movie to friend
    public MovieRecommendation recommendMovie(Long userId, Long friendId, String movieId) {
        if (!isFriend(userId, friendId)) {
            throw new IllegalStateException("You can recommend movies only to friends.");
        }

        MovieRecommendation rec = new MovieRecommendation(userId, friendId, movieId);
        return movieRecommendationRepository.save(rec);
    }

    public List<MovieRecommendation> getMoviesRecommendedToUser(Long userId) {
        return movieRecommendationRepository.findByFriendId(userId);
    }

    public List<MovieRecommendation> getMoviesRecommendedByUser(Long userId) {
        return movieRecommendationRepository.findByUserId(userId);
    }

    // ✅ Friend check
    public boolean isFriend(Long userId, Long otherUserId) {
        return friendRepository.existsByUserIdAndFriendId(userId, otherUserId);
    }
}
