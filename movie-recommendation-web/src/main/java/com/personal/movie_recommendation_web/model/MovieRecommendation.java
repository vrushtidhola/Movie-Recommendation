package com.personal.movie_recommendation_web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long friendId;
    private String movieName;
    public MovieRecommendation(Long userId, Long friendId, String movieName) {
        this.userId = userId;
        this.friendId = friendId;
        this.movieName = movieName;
    }

}
