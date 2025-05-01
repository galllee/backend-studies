package com.example.todomate_clone.notification.event;

import com.example.todomate_clone.todo.todo.domain.Todo;
import com.example.todomate_clone.user.domain.User;
import lombok.Getter;
import org.springframework.web.service.annotation.GetExchange;

@Getter
public class TodoCompletedEvent {
    private final User user;
    private final Todo todo;

    public TodoCompletedEvent(User user, Todo todo) {
        this.user = user;
        this.todo = todo;
    }
}