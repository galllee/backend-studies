package com.example.todomate_clone.todo.todo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class UpdateTodoReminderTime {
    private LocalTime reminderTime;
}
