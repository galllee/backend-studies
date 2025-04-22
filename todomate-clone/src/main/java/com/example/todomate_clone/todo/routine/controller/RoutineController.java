package com.example.todomate_clone.todo.routine.controller;

import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.global.util.AuthUtil;
import com.example.todomate_clone.todo.routine.dto.request.CreateRoutineRequest;
import com.example.todomate_clone.todo.routine.dto.request.UpdateRoutineRequest;
import com.example.todomate_clone.todo.routine.service.RoutineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RoutineController {

    private final RoutineService routineService;

    @PostMapping("/api/v1/categories/{categoryId}/routines")
    public ResponseEntity<ApiResponse<?>> createRoutine(@PathVariable Long categoryId, @Valid @RequestBody CreateRoutineRequest request) {
        routineService.createRoutine(AuthUtil.getLoginUsername(), categoryId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("루틴 생성 완료"));
    }

    @PutMapping("/api/v1/routines/{routineId}")
    public ResponseEntity<ApiResponse> updateRoutine(@PathVariable Long routineId, @Valid @RequestBody UpdateRoutineRequest request) {
        routineService.updateRoutine(routineId, request);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("루틴 수정 완료"));
    }

    @DeleteMapping("/api/v1/routines/{routineId}")
    public ResponseEntity<ApiResponse> deleteRoutine(@PathVariable Long routineId) {
        routineService.deleteRoutine(routineId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("루틴 삭제 완료"));
    }
}
