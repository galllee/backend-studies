package com.example.todomate_clone.todo.category.controller;

import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.global.util.AuthUtil;
import com.example.todomate_clone.todo.category.dto.request.CreateCategoryRequest;
import com.example.todomate_clone.todo.category.dto.request.EditCategoryRequest;
import com.example.todomate_clone.todo.category.service.CategoryService;
import com.example.todomate_clone.todo.category.dto.request.UpdateCategoryOrdersRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    // create category
    @PostMapping("/api/v1/categories")
    public ResponseEntity<ApiResponse<?>> createCategory(@RequestBody CreateCategoryRequest request) {
        categoryService.createCategory(request, AuthUtil.getLoginUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("카테고리 생성 완료"));
    }

    // edit category
    @PutMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<ApiResponse<?>> editCategory(@RequestBody EditCategoryRequest request, @PathVariable Long categoryId) {
        AuthUtil.validateUser();
        categoryService.editCategory(request, categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 수정 완료"));
    }

    // pause category
    @PatchMapping("/api/v1/categories/{categoryId}/complete")
    public ResponseEntity<ApiResponse<?>> completeCategory(@PathVariable Long categoryId) {
        AuthUtil.validateUser();
        categoryService.completeCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 종료"));
    }

    // resume category
    @PatchMapping("/api/v1/categories/{categoryId}/resume")
    public ResponseEntity<ApiResponse<?>> resumeCategory(@PathVariable Long categoryId) {
        AuthUtil.validateUser();
        categoryService.resumeCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 재개"));
    }

    // delete category
    @DeleteMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<ApiResponse<?>> deleteCategory(@PathVariable Long categoryId) {
        AuthUtil.validateUser();
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 삭제 완료"));
    }

    @PatchMapping("/api/v1/categories/order")
    public ResponseEntity<ApiResponse<?>> updateCategoryOrders(@RequestBody UpdateCategoryOrdersRequest request) {
        categoryService.updateCategoryOrders(AuthUtil.getLoginUsername(), request);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 순서 변경 완료"));
    }
}
