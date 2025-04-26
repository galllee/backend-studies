package com.example.todomate_clone.todo.todo.service;

import com.example.todomate_clone.todo.category.domain.Category;
import com.example.todomate_clone.todo.category.dto.request.TodoOrderItem;
import com.example.todomate_clone.todo.category.dto.request.UpdateTodoOrdersRequest;
import com.example.todomate_clone.todo.category.repository.CategoryRepository;
import com.example.todomate_clone.todo.todo.domain.Todo;
import com.example.todomate_clone.todo.todo.dto.response.TodoGroupByCategoryResponse;
import com.example.todomate_clone.todo.todo.repository.TodoRepository;
import com.example.todomate_clone.todo.todo.dto.request.*;
import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

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

    @Transactional
    public void archiveTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todo.archive();
    }

    @Transactional
    public void repeatTodoToday(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todoRepository.save(
                Todo.builder()
                        .categoryId(todo.getCategoryId())
                        .userId(todo.getUserId())
                        .title(todo.getTitle())
                        .date(LocalDate.now())
                        .reminderTime(todo.getReminderTime())
                .build()
        );
    }

    @Transactional
    public void repeatTodo(Long todoId, LocalDate date) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 투두를 찾을 수 없습니다."));

        todoRepository.save(
                Todo.builder()
                        .categoryId(todo.getCategoryId())
                        .userId(todo.getUserId())
                        .title(todo.getTitle())
                        .date(date)
                        .reminderTime(todo.getReminderTime())
                        .build()
        );
    }

    public List<TodoGroupByCategoryResponse> getTodosByDateGroupedByCategory(String email, LocalDate date) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Map<Long, List<Todo>> todosByCategory = todoRepository.findAllByUserIdAndDate(user.getId(), date)
                .stream().collect(Collectors.groupingBy(Todo::getCategoryId));

        return todosByCategory.entrySet().stream().flatMap(
                entry -> {
                    Category category = categoryRepository.findById(entry.getKey())
                                    .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

                    return entry.getValue().stream().map(todo -> TodoGroupByCategoryResponse.builder()
                            .categoryName(category.getName())
                            .title(todo.getTitle())
                            .elapsedTime(todo.getElapsedTime())
                            .memo(todo.getMemo())
                            .status(todo.getStatus())
                            .build()
                    );
                }).toList();

        // 미완료 -> 완료 -> 수동루틴 연하게 보여주는 순서


    }

    @Transactional
    public void updateTodoOrders(String email, UpdateTodoOrdersRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        List<Todo> todos = todoRepository.findAllByUserIdAndDate(user.getId(), request.getDate());

        for(Todo todo : todos) {
            TodoOrderItem matched = request.getOrderItems()
                            .stream().filter(
                                    orderItem -> todo.getId().equals(orderItem.getTodoId())
                    ).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("해당 todo가 존재하지 않습니다."));

            todo.updateOrderNum(matched.getOrderNum());
            todo.updateCategory(matched.getCategoryId());
        }
    }
}
