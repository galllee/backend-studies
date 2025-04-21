package com.example.todomate_clone.todo.controller;

import com.example.todomate_clone.global.security.jwt.service.JwtService;
import com.example.todomate_clone.global.util.AuthUtil;
import com.example.todomate_clone.todo.dto.request.*;
import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.todo.service.TodoService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    private final JwtService jwtService;

    // create category
    @PostMapping("/api/v1/categories")
    public ResponseEntity<ApiResponse<?>> createCategory(@RequestBody CreateCategoryRequest request) {
        todoService.createCategory(request, AuthUtil.getLoginUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("카테고리 생성 완료"));
    }

    // edit category
    @PutMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<ApiResponse<?>> editCategory(@RequestBody EditCategoryRequest request, @PathVariable Long categoryId) {
        AuthUtil.validateUser();
        todoService.editCategory(request, categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 수정 완료"));
    }

    // pause category
    @PatchMapping("/api/v1/categories/{categoryId}/complete")
    public ResponseEntity<ApiResponse<?>> completeCategory(@PathVariable Long categoryId) {
        AuthUtil.validateUser();
        todoService.completeCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 종료"));
    }

    // resume category
    @PatchMapping("/api/v1/categories/{categoryId}/resume")
    public ResponseEntity<ApiResponse<?>> resumeCategory(@PathVariable Long categoryId) {
        AuthUtil.validateUser();
        todoService.resumeCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 재개"));
    }

    // delete category
    @DeleteMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<ApiResponse<?>> deleteCategory(@PathVariable Long categoryId) {
        AuthUtil.validateUser();
        todoService.deleteCategory(categoryId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("카테고리 삭제 완료"));
    }

    // create todo
    @PostMapping("/api/v1/categories/{categoryId}/todos")
    public ResponseEntity<ApiResponse<?>> createTodo(@PathVariable Long categoryId, @RequestBody CreateTodoRequest request) {
        todoService.createTodo(categoryId, request, AuthUtil.getLoginUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("투두 생성 완료"));
    }

    // edit todo
    @PatchMapping("/api/v1/todos/{todoId}")
    public ResponseEntity<ApiResponse<?>> editTodo(@PathVariable Long todoId, @RequestBody EditTodoRequest request) {
        AuthUtil.validateUser();
        todoService.editTodo(todoId, request.getTitle());

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("투두 편집 완료"));
    }

    //delete todo
    @DeleteMapping("/api/v1/todos/{todoId}")
    public ResponseEntity<ApiResponse<?>> deleteTodo(@PathVariable Long todoId){
        AuthUtil.validateUser();

        todoService.deleteTodo(todoId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("투두 삭제 완료"));
    }

    // add memo on todo
    @PatchMapping("/api/v1/todos/{todoId}/memo")
    public ResponseEntity<ApiResponse<?>> updateTodoMemo(@PathVariable Long todoId, @RequestBody UpdateTodoMemoRequest request) {
        AuthUtil.validateUser();
        todoService.updateTodoMemo(todoId, request);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("메모 추가 완료"));
    }

    // add reminder time on todo
    @PatchMapping("/api/v1/todos/{todoId}/reminder")
    public ResponseEntity<ApiResponse<?>> updateTodoReminderTime(@PathVariable Long todoId, @RequestBody UpdateTodoReminderTime request) {
        AuthUtil.validateUser();
        todoService.updateTodoReminderTime(todoId, request);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("알림 시간 추가 완료"));
    }

    // save elapsed time when paused
    @PatchMapping("/api/v1/todos/{todoId}/timer/pause")
    public ResponseEntity<ApiResponse<?>> pauseTodoTimer(@PathVariable Long todoId, @RequestBody PauseTodoTimerRequest request) {
        AuthUtil.validateUser();
        todoService.pauseTodoTimer(todoId, request);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("타이머 시간 저장 완료"));
    }

    // save elapsed time when completed and complete todo
    @PatchMapping("/api/v1/todos/{todoId}/timer/complete")
    public ResponseEntity<ApiResponse<?>> completeTodoTimer(@PathVariable Long todoId, @RequestBody CompleteTodoTimerRequest request) {
        AuthUtil.validateUser();
        todoService.completeTodoTimer(todoId, request);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("타이머 시간 저장 및 투두 완료"));
    }

    // do today
    @PatchMapping("/api/v1/todos/{todoId}/schedule/today")
    public ResponseEntity<ApiResponse<?>> scheduleTodoForToday(@PathVariable Long todoId) {
        todoService.scheduleTodoForToday(todoId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("오늘로 일정 이동 완료"));
    }

    // do tomorrow
    @PatchMapping("/api/v1/todos/{todoId}/schedule/tomorrow")
    public ResponseEntity<ApiResponse<?>> scheduleTodoForTomorrow(@PathVariable Long todoId) {
        todoService.scheduleTodoForTomorrow(todoId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("내일로 일정 이동 완료"));
    }

    // change date
    @PatchMapping("/api/v1/todos/{todoId}/schedule")
    public ResponseEntity<ApiResponse<?>> updateTodoSchedule(@PathVariable Long todoId, @RequestBody UpdateTodoScheduleRequest request) {
        todoService.updateTodoSchedule(todoId, request);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("일정 이동 완료"));
    }
}
