package com.example.todomate_clone.todo.todo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateTodoMemoRequest {
    private String memo;
    private boolean isMemoPrivate;
}
