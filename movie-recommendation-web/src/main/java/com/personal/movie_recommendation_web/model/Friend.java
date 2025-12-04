package com.personal.movie_recommendation_web.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "friends")

public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long friendId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public void setUserId(Long userId) {
        this.userId=userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId=friendId;
    }
}
