package com.example.todomate_clone.todo.controller;

import com.example.todomate_clone.global.security.jwt.service.JwtService;
import com.example.todomate_clone.global.util.AuthUtil;
import com.example.todomate_clone.todo.dto.request.CreateCategoryRequest;
import com.example.todomate_clone.todo.dto.request.CreateTodoRequest;
import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.todo.dto.request.EditCategoryRequest;
import com.example.todomate_clone.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    private final JwtService jwtService;

    @PostMapping("/api/v1/categories")
    public ResponseEntity<ApiResponse<?>> createCategory(@RequestBody CreateCategoryRequest request) {
        todoService.createCategory(request, AuthUtil.getLoginUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("카테고리 생성 완료"));
    }

    @PutMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<ApiResponse<?>> editCategory(@RequestBody EditCategoryRequest request, @PathVariable Long categoryId) {
        AuthUtil.validateUser();

        todoService.editCategory(request, categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 수정 완료"));
    }

    @PatchMapping("/api/v1/categories/{categoryId}/pause")
    public ResponseEntity<ApiResponse<?>> pauseCategory(@PathVariable Long categoryId) {
        AuthUtil.validateUser();

        todoService.pauseCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 종료"));
    }

    @PatchMapping("/api/v1/categories/{categoryId}/resume")
    public ResponseEntity<ApiResponse<?>> resumeCategory(@PathVariable Long categoryId) {
        AuthUtil.validateUser();

        todoService.resumeCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 재개"));
    }

    @DeleteMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<ApiResponse<?>> deleteCategory(@PathVariable Long categoryId) {
        AuthUtil.validateUser();

        todoService.deleteCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 삭제 완료"));
    }

    @PostMapping("/api/v1/categories/{categoryId}/todos")
    public ResponseEntity<ApiResponse<?>> createTodo(@PathVariable Long categoryId, @RequestBody CreateTodoRequest request) {
        todoService.createTodo(categoryId, request, AuthUtil.getLoginUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("투두 생성 완료"));
    }
}
