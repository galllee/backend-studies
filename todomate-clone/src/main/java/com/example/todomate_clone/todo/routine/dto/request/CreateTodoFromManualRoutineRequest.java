package com.example.todomate_clone.todo.routine.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateTodoFromManualRoutineRequest {
    private LocalDate date;
}
