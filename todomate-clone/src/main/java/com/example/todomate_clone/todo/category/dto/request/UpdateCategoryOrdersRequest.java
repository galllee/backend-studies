package com.example.todomate_clone.todo.category.dto.request;

import com.example.todomate_clone.todo.category.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateCategoryOrdersRequest {
    private List<CategoryOrderItem> orderItems;
}
