package com.example.todomate_clone.todo.todo.dto.response;

import com.example.todomate_clone.todo.todo.domain.TodoStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
//@NoArgsConstructor
public class TodoGroupByCategoryResponse {
    private String categoryName;
    private String title;
    private Long elapsedTime;
    private String memo;
    private TodoStatus status;

    @Builder
    public TodoGroupByCategoryResponse(String categoryName, String title, Long elapsedTime, String memo, TodoStatus status) {
        this.categoryName = categoryName;
        this.title = title;
        this.elapsedTime = elapsedTime;
        this.memo = memo;
        this.status = status;
    }
}
