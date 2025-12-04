package com.personal.movie_recommendation_web.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movie_recommendations")

public class MovieRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;   // recommender
    private Long friendId; // receiver
    private String movieId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public void setUserId(Long userId) {
        this.userId=userId;
    }

    public void setFriendId(Long friendId) {
        this.friendId=friendId;
    }

    public void setMovieId(String movieId) {
        this.movieId=movieId;
    }
}
