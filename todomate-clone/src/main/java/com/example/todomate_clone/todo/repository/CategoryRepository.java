package com.example.todomate_clone.todo.repository;

import com.example.todomate_clone.todo.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
