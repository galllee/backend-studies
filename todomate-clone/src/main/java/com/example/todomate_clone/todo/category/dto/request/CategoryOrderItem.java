package com.example.todomate_clone.todo.category.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryOrderItem {
    private Long categoryId;
    private Long orderNum;
}
