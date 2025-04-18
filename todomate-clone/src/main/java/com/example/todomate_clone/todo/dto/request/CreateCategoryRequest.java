package com.example.todomate_clone.todo.dto.request;

import com.example.todomate_clone.todo.domain.Visibility;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateCategoryRequest {
    private String name;
    private Visibility visibility;
    private String color;
}
