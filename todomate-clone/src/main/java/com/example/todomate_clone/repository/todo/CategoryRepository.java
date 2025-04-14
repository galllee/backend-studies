package com.example.todomate_clone.repository.todo;

import com.example.todomate_clone.domain.todo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
