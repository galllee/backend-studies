package com.example.todomate_clone.todo.category.service;

import com.example.todomate_clone.todo.category.domain.Category;
import com.example.todomate_clone.todo.category.domain.Visibility;
import com.example.todomate_clone.todo.category.dto.request.CreateCategoryRequest;
import com.example.todomate_clone.todo.category.dto.request.EditCategoryRequest;
import com.example.todomate_clone.todo.category.repository.CategoryRepository;
import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public void createCategory(CreateCategoryRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
        categoryRepository.save(
                Category.builder()
                        .userId(user.getId())
                        .name(request.getName())
                        .visibility(request.getVisibility())
                        .color(request.getColor())
                        .build()
        );
    }

    @Transactional
    public void editCategory(EditCategoryRequest request, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        category.updateCategory(request.getName(), Visibility.valueOf(request.getVisibility()), request.getColor());
    }

    @Transactional
    public void completeCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        category.completeCategory();
    }

    @Transactional
    public void resumeCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        category.resumeCategory();
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        categoryRepository.delete(category);
    }

}
