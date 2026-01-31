package com.personal.movie_recommendation_web.dto;

import lombok.Data;
@Data
public class RecommendMovieRequest {

    private Long userId;
    private Long friendId;

    private String movieTitle;
    private Long movieTmdbId;

    private Integer rating;
    private String comment;

    private Boolean visibleToFriends;
}
