package com.personal.movie_recommendation_web.repository;

import com.personal.movie_recommendation_web.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUserId(Long userId);
    boolean existsByUserIdAndFriendId(Long userId, Long friendId);

}
