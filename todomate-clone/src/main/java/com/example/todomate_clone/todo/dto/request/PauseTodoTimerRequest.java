package com.example.todomate_clone.todo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PauseTodoTimerRequest {
    private Long elapsedTime;
}
