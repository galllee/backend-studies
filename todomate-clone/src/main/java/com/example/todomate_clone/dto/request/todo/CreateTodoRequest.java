package com.example.todomate_clone.dto.request.todo;

import com.example.todomate_clone.domain.todo.TodoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CreateTodoRequest {
    private String title;
    private LocalDate date;
}
