package com.example.todomate_clone.todo.todo.repository;

import com.example.todomate_clone.todo.todo.domain.Todo;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    public List<Todo> findAllByUserIdAndDateAndIsArchivedFalse(Long userId, LocalDate date);
}
