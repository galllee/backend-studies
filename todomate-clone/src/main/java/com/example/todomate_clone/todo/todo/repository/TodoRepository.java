package com.example.todomate_clone.todo.todo.repository;

import com.example.todomate_clone.todo.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
