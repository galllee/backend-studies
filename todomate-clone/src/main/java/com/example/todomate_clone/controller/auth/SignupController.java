package com.example.todomate_clone.controller.auth;

import com.example.todomate_clone.dto.request.auth.SignupCompleteRequest;
import com.example.todomate_clone.dto.request.auth.SignupRequest;
import com.example.todomate_clone.dto.request.user.CreateUserRequest;
import com.example.todomate_clone.dto.response.ApiResponse;
import com.example.todomate_clone.service.auth.SignupService;
import com.example.todomate_clone.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class SignupController {
    private final UserService userService;
    private final SignupService signupService;

    @PostMapping("/api/v1/signup/request")
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

    @GetMapping("/api/v1/signup/verify")
    public ResponseEntity<ApiResponse> signupVerify(@RequestParam String token) {
        signupService.signupVerify(token);
        return ResponseEntity.ok(ApiResponse.createSuccess("이메일 인증 완료", token));
    }

    @PostMapping("/api/v1/signup/complete")
    public ResponseEntity<ApiResponse> signupComplete(@RequestBody SignupCompleteRequest request) {
        signupService.signupComplete(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("유저 생성 완료"));
    }

}
