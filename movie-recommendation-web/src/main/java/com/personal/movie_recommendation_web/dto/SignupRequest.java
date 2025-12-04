package com.personal.movie_recommendation_web.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String email;
    private String password;
}
