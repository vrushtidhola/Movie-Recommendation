package com.personal.movie_recommendation_web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ImplicitFriendRecommendation {

    private Long movieId;
    private int recommendedByCount;
    private List<Long> friendIds;
}
