package com.example.todomate_clone.auth.controller;

import com.example.todomate_clone.auth.dto.request.SignupCompleteRequest;
import com.example.todomate_clone.auth.dto.request.SignupRequest;
import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.auth.service.SignupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class SignupController {
    private final SignupService signupService;

    @PostMapping("/api/v1/auth/signup/request")
    public ResponseEntity<ApiResponse> signupRequest(
            @RequestBody @Valid SignupRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.createFail(bindingResult));
        }
        signupService.signupRequest(request);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("이메일 인증 링크가 발송되었습니다."));
    }

    @GetMapping("/api/v1/auth/signup/complete")
    public ResponseEntity<ApiResponse> signupVerify(@RequestParam String token) {
        signupService.signupVerify(token);
        return ResponseEntity.ok(ApiResponse.createSuccess("이메일 인증 완료", token));
    }

    @PostMapping("/api/v1/auth/signup/complete")
    public ResponseEntity<ApiResponse> signupComplete(@RequestBody SignupCompleteRequest request) {
        signupService.signupComplete(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("유저 생성 완료"));
    }

}
