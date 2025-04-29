package com.example.todomate_clone.friend.repository;

import com.example.todomate_clone.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    public Optional<Friend> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
