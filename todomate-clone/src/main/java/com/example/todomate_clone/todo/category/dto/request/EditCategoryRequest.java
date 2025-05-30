package com.example.todomate_clone.todo.category.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EditCategoryRequest {
    private String name;
    private String visibility;
    private String color;
}
