package com.example.todomate_clone.todo.todo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CreateTodoRequest {
    private String title;
    private LocalDate date;
}
