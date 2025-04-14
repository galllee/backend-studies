package com.example.todomate_clone.repository.todo;

import com.example.todomate_clone.domain.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
