package com.example.todomate_clone.todo.category.service;

import com.example.todomate_clone.todo.category.domain.Category;
import com.example.todomate_clone.todo.category.domain.CategoryStatus;
import com.example.todomate_clone.todo.category.domain.Visibility;
import com.example.todomate_clone.todo.category.dto.request.CategoryOrderItem;
import com.example.todomate_clone.todo.category.dto.request.CreateCategoryRequest;
import com.example.todomate_clone.todo.category.dto.request.EditCategoryRequest;
import com.example.todomate_clone.todo.category.repository.CategoryRepository;
import com.example.todomate_clone.todo.category.dto.request.UpdateCategoryOrdersRequest;
import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public void createCategory(Long userId, CreateCategoryRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
        categoryRepository.save(
                Category.builder()
                        .user(user)
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

    @Transactional
    public void updateCategoryOrders(Long userId, UpdateCategoryOrdersRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        List<Category> categories = categoryRepository.findAllByUserIdAndStatus(user.getId(), CategoryStatus.IN_PROGRESS);

        for(Category category : categories) {
            CategoryOrderItem matched = request.getOrderItems()
                    .stream()
                    .filter(orderItem -> orderItem.getCategoryId().equals(category.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

            category.updateOrderNum(matched.getOrderNum());
        }
    }
}
