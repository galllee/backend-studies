package com.example.todomate_clone.todo.repository;

import com.example.todomate_clone.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
