package com.example.todomate_clone.todo.todo.service;

import com.example.todomate_clone.todo.todo.domain.Todo;
import com.example.todomate_clone.todo.todo.repository.TodoRepository;
import com.example.todomate_clone.todo.todo.dto.request.*;
import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public void createTodo(Long categoryId, CreateTodoRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        todoRepository.save(
                Todo.builder()
                        .categoryId(categoryId)
                        .userId(user.getId())
                        .title(request.getTitle())
                        .date(request.getDate())
                        .build()
        );
    }

    @Transactional
    public void editTodo(Long todoId, String title) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.editTodo(title);
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todoRepository.delete(todo);
    }

    @Transactional
    public void updateTodoMemo(Long todoId, UpdateTodoMemoRequest request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateTodoMemo(request.getMemo(), request.isMemoPrivate());
    }

    @Transactional
    public void updateTodoReminderTime(Long todoId, UpdateTodoReminderTime request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateTodoReminderTime(request.getReminderTime());
    }

    @Transactional
    public void pauseTodoTimer(Long todoId, PauseTodoTimerRequest request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateElapsedTime(request.getElapsedTime());
    }

    @Transactional
    public void completeTodoTimer(Long todoId, CompleteTodoTimerRequest request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateElapsedTime(request.getElapsedTime());
        todo.markAsCompleted();
    }

    @Transactional
    public void scheduleTodoForToday(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateDate(LocalDate.now());
    }

    @Transactional
    public void scheduleTodoForTomorrow(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateDate(LocalDate.now().plusDays(1));
    }

    @Transactional
    public void updateTodoSchedule(Long todoId, UpdateTodoScheduleRequest request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.updateDate(request.getNewDate());
    }
}
