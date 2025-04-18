package com.example.todomate_clone.user.controller;

import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.dto.request.CreateUserRequest;
import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/users")
    public ResponseEntity<ApiResponse<?>> createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("유저 생성 완료"));
    }
}
