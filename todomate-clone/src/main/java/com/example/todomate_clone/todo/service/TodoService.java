package com.example.todomate_clone.todo.service;

import com.example.todomate_clone.todo.domain.Category;
import com.example.todomate_clone.todo.domain.Todo;
import com.example.todomate_clone.todo.dto.request.CreateCategoryRequest;
import com.example.todomate_clone.todo.dto.request.CreateTodoRequest;
import com.example.todomate_clone.todo.repository.CategoryRepository;
import com.example.todomate_clone.todo.repository.TodoRepository;
import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TodoService {
    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public void createCategory(CreateCategoryRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        categoryRepository.save(
                Category.builder()
                        .userId(user.getId())
                        .name(request.getName())
                        .visibility(request.getVisibility())
                        .color(request.getColor())
                        .build()
        );
    }

    public void createTodo(Long categoryId, CreateTodoRequest request) {
        todoRepository.save(
                Todo.builder()
                        .categoryId(categoryId)
                        .title(request.getTitle())
                        .date(request.getDate())
                        .build()
        );
    }
}
