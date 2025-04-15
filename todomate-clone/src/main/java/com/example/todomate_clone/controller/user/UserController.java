package com.example.todomate_clone.controller.user;

import com.example.todomate_clone.domain.user.User;
import com.example.todomate_clone.dto.request.user.CreateUserRequest;
import com.example.todomate_clone.dto.response.ApiResponse;
import com.example.todomate_clone.service.user.UserService;
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

    @PostMapping("/api/v1/users") //생성
    public ResponseEntity<ApiResponse<?>> createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("유저 생성 완료"));
    }
}
