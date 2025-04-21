package com.example.todomate_clone.todo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UpdateTodoReminderTime {
    private LocalDateTime reminderTime;
}
