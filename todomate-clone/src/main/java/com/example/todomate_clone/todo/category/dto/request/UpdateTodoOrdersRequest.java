package com.example.todomate_clone.todo.category.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateTodoOrdersRequest {
    private LocalDate date;
    private List<TodoOrderItem> orderItems;
}
