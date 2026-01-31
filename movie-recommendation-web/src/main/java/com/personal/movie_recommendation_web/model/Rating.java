package com.personal.movie_recommendation_web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ratings", schema = "recommandation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "movie_tmdb_id")
    private Long movieTmdbId;

    private Integer stars;

    private String review;

    @Column(name = "visible_to_friends")
    private Boolean visibleToFriends;

    @Column(name = "created_at")
    private Long createdAt;
}
