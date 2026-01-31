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
    private String movieTitle;
    private Integer rating;
    private String comment;
    @Column(name = "movie_tmdb_id")
    private Long movieTmdbId;   // ✅ REQUIRED

    private Long createdAt;

    public MovieRecommendation(Long userId, Long friendId, String movieTitle,Integer rating,String comment,Long createdAt) {
        this.userId = userId;
        this.friendId = friendId;
        this.movieTitle = movieTitle;
        this.rating=rating;
        this.comment=comment;
        this.createdAt=createdAt;
    }

}
