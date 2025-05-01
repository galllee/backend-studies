package com.example.todomate_clone.notification.event.todoLike;

import com.example.todomate_clone.todo.todo.domain.Todo;
import com.example.todomate_clone.user.domain.User;
import lombok.Getter;

@Getter
public class TodoLikeEvent {
    private User fromUser;
    private Todo todo;

    public TodoLikeEvent(User fromUser, Todo todo) {
        this.fromUser = fromUser;
        this.todo = todo;
    }
}
