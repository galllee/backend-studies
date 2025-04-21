package com.example.todomate_clone.todo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UpdateTodoScheduleRequest {
    private LocalDate newDate;
}
