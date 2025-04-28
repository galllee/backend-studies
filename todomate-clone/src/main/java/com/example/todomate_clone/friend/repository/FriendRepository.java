package com.example.todomate_clone.friend.repository;

import com.example.todomate_clone.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
