package com.example.todomate_clone.todo.todo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoOrderItem {
    private Long categoryId;
    private Long todoId;
    private Long orderNum;
}
