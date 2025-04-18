package com.example.todomate_clone.auth.controller;

import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.auth.dto.request.SigninRequest;
import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.auth.service.SigninService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class SigninController {
    private final SigninService signinService;

    @PostMapping("/api/v1/auth/signin")
    public ResponseEntity<ApiResponse> signin(
            @RequestBody @Valid SigninRequest request,
            HttpSession session
            ) {
        User user = signinService.signin(request);
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(1800);

        return ResponseEntity.ok(ApiResponse.createSuccess("로그인 성공"));
    }

    @PostMapping("/api/v1/auth/signout")
    public ResponseEntity<ApiResponse> signout(HttpSession session) {
        User user = (User) session.getAttribute("user");
        session.invalidate();
        return ResponseEntity.ok(ApiResponse.createSuccess("로그아웃 완료"));
    }
}