package com.example.todomate_clone.dto.request.todo;

import com.example.todomate_clone.domain.todo.Visibility;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateCategoryRequest {
    private String name;
    private Visibility visibility;
    private String color;
}
