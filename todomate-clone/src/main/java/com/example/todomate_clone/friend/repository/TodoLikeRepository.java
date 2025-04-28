package com.example.todomate_clone.friend.repository;

import com.example.todomate_clone.friend.domain.TodoLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoLikeRepository extends JpaRepository<TodoLike, Long> {
}
