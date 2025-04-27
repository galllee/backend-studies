package com.example.todomate_clone.todo.category.repository;

import com.example.todomate_clone.todo.category.domain.Category;
import com.example.todomate_clone.todo.category.domain.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findAllByUserIdAndStatus(Long userId, CategoryStatus status);
}
