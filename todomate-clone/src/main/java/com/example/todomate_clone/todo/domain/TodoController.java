package com.example.todomate_clone.todo.controller;

import com.example.todomate_clone.todo.dto.request.CreateCategoryRequest;
import com.example.todomate_clone.todo.dto.request.CreateTodoRequest;
import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/api/v1/categories")
    public ResponseEntity<ApiResponse<?>> createCategory(@RequestBody CreateCategoryRequest request) {
        todoService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("카테고리 생성 완료"));
    }

    @PostMapping("/api/v1/categories/{categoryId}/todos")
    public ResponseEntity<ApiResponse<?>> createTodo(@PathVariable Long categoryId, @RequestBody CreateTodoRequest request) {
        todoService.createTodo(categoryId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("투두 생성 완료"));
    }
}
