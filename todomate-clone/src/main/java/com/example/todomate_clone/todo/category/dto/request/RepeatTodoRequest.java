package com.example.todomate_clone.todo.category.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RepeatTodoRequest {
    private LocalDate date;
}
