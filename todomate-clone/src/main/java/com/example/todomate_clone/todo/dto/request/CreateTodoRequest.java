package com.example.todomate_clone.todo.dto.request;

import com.example.todomate_clone.todo.domain.TodoStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CreateTodoRequest {
    private String title;
    private LocalDate date;
}
