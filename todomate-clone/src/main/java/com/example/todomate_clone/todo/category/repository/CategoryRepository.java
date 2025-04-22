package com.example.todomate_clone.todo.category.repository;

import com.example.todomate_clone.todo.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
