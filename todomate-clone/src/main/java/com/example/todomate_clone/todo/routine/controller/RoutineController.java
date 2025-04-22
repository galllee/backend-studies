package com.example.todomate_clone.todo.routine.controller;

import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.global.util.AuthUtil;
import com.example.todomate_clone.todo.routine.dto.request.CreateRoutineRequest;
import com.example.todomate_clone.todo.routine.service.RoutineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RoutineController {

    private final RoutineService routineService;

    @PostMapping("/api/v1/categories/{categoryId}/routine")
    public ResponseEntity<ApiResponse<?>> createRoutine(@PathVariable Long categoryId, @Valid @RequestBody CreateRoutineRequest request) {
        routineService.createRoutine(AuthUtil.getLoginUsername(), categoryId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("루틴 생성 완료"));
    }
}
