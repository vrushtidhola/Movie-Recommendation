package com.personal.movie_recommendation_web.web.dto;

import lombok.Data;

@Data
public class RatingRequest {

    private Long userId;
    private Long movieTmdbId;

    private Integer stars;
    private String review;
    private Boolean visibleToFriends;
}
