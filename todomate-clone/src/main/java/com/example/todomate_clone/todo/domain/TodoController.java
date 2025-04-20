package com.example.todomate_clone.todo.controller;

import com.example.todomate_clone.global.security.jwt.service.JwtService;
import com.example.todomate_clone.global.security.model.UserDetailsImpl;
import com.example.todomate_clone.todo.dto.request.CreateCategoryRequest;
import com.example.todomate_clone.todo.dto.request.CreateTodoRequest;
import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.todo.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;
    private final JwtService jwtService;

    @PostMapping("/api/v1/categories")
    public ResponseEntity<ApiResponse<?>> createCategory(@RequestBody CreateCategoryRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getUsername();

        todoService.createCategory(request, email);
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
