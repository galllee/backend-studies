package com.example.todomate_clone.todo.todo.controller;

import com.example.todomate_clone.global.security.jwt.service.JwtService;
import com.example.todomate_clone.global.util.AuthUtil;
import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.todo.category.dto.request.RepeatTodoRequest;
import com.example.todomate_clone.todo.category.dto.request.UpdateTodoOrdersRequest;
import com.example.todomate_clone.todo.todo.dto.response.TodoGroupByCategoryResponse;
import com.example.todomate_clone.todo.todo.service.TodoService;
import com.example.todomate_clone.todo.todo.dto.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    private final JwtService jwtService;

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

    @PatchMapping("/api/v1/todos/{todoId}/timer/reset")
    public ResponseEntity<ApiResponse<?>> resetTodoTimer(@PathVariable Long todoId) {
        AuthUtil.validateUser();
        todoService.resetTodoTimer(todoId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("타이머 시간 초기화 완료"));
    }

    // do today
    @PatchMapping("/api/v1/todos/{todoId}/schedule/today")
    public ResponseEntity<ApiResponse<?>> scheduleTodoForToday(@PathVariable Long todoId) {
        AuthUtil.validateUser();
        todoService.scheduleTodoForToday(todoId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("오늘로 일정 이동 완료"));
    }

    // do tomorrow
    @PatchMapping("/api/v1/todos/{todoId}/schedule/tomorrow")
    public ResponseEntity<ApiResponse<?>> scheduleTodoForTomorrow(@PathVariable Long todoId) {
        AuthUtil.validateUser();
        todoService.scheduleTodoForTomorrow(todoId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("내일로 일정 이동 완료"));
    }

    // change date
    @PatchMapping("/api/v1/todos/{todoId}/schedule")
    public ResponseEntity<ApiResponse<?>> updateTodoSchedule(@PathVariable Long todoId, @RequestBody UpdateTodoScheduleRequest request) {
        AuthUtil.validateUser();
        todoService.updateTodoSchedule(todoId, request);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("일정 이동 완료"));
    }

    // archive
    @PatchMapping("/api/v1/todos/{todoId}/archive")
    public ResponseEntity<ApiResponse<?>> archiveTodo(@PathVariable Long todoId) {
        AuthUtil.validateUser();
        todoService.archiveTodo(todoId);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("보관함으로 이동 완료"));
    }

    // do it again
    @PostMapping("/api/v1/todos/{todoId}/repeat-today")
    public ResponseEntity<ApiResponse<?>> repeatTodoToday(@PathVariable Long todoId) {
        AuthUtil.validateUser();
        todoService.repeatTodoToday(todoId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("오늘 또하기 완료"));
    }

    @PostMapping("/api/v1/todos/{todoId}/repeat")
    public ResponseEntity<ApiResponse<?>> repeatTodo(
            @PathVariable Long todoId,
            @RequestBody RepeatTodoRequest request) {
        AuthUtil.validateUser();
        todoService.repeatTodo(todoId, request.getDate());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("다른 날 또하기 완료"));
    }

    @GetMapping("/api/v1/todos")
    public ResponseEntity<ApiResponse<?>> getTodosByDate(
            @RequestParam LocalDate date
            ) {
        List<TodoGroupByCategoryResponse> todoResponses = todoService.getTodosByDateGroupedByCategory(AuthUtil.getLoginUsername(), date);

        return ResponseEntity.ok(ApiResponse.createSuccess("투두 보여주기 완료", todoResponses));
    }

    @PatchMapping("/api/v1/todos/order")
    public ResponseEntity<ApiResponse<?>> updateTodoOrders(@RequestBody UpdateTodoOrdersRequest request) {
        todoService.updateTodoOrders(AuthUtil.getLoginUsername(), request);

        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("투두 순서 변경 완료"));
    }
}
