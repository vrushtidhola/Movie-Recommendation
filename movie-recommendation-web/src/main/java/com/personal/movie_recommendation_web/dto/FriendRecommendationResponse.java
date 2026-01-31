package com.personal.movie_recommendation_web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRecommendationResponse {

    private String source;           // EXPLICIT | IMPLICIT
    private Long movieId;
    private String movieTitle;
    private Long movieTmdbId;// For explicit
    private Integer rating;
    private String comment;
    private String recommendedBy;
    private Integer recommendedByCount;
    private Long createdAt;
}
