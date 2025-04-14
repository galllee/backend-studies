package com.example.todomate_clone.controller.todo;

import com.example.todomate_clone.dto.request.todo.CreateCategoryRequest;
import com.example.todomate_clone.dto.request.todo.CreateTodoRequest;
import com.example.todomate_clone.dto.response.ApiResponse;
import com.example.todomate_clone.service.todo.TodoService;
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
