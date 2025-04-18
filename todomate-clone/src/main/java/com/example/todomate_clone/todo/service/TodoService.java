package com.example.todomate_clone.todo.service;

import com.example.todomate_clone.todo.domain.Category;
import com.example.todomate_clone.todo.domain.Todo;
import com.example.todomate_clone.todo.dto.request.CreateCategoryRequest;
import com.example.todomate_clone.todo.dto.request.CreateTodoRequest;
import com.example.todomate_clone.todo.repository.CategoryRepository;
import com.example.todomate_clone.todo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TodoService {
    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;

    public void createCategory(CreateCategoryRequest request) {
        categoryRepository.save(
                Category.builder()
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
