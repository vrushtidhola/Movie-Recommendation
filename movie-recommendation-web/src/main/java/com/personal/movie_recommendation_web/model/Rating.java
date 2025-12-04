package com.personal.movie_recommendation_web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="ratings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Rating {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;   // FK to your user table (assumed managed elsewhere)
    private Long movieTmdbId; // TMDB movie id
    private Integer stars; // 1-5
    private String review;
    private Boolean visibleToFriends = true;
    private Long createdAt;
}
