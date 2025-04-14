package com.example.todomate_clone.service.todo;

import com.example.todomate_clone.domain.todo.Category;
import com.example.todomate_clone.domain.todo.Todo;
import com.example.todomate_clone.dto.request.todo.CreateCategoryRequest;
import com.example.todomate_clone.dto.request.todo.CreateTodoRequest;
import com.example.todomate_clone.repository.todo.CategoryRepository;
import com.example.todomate_clone.repository.todo.TodoRepository;
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
