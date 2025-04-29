package com.example.todomate_clone.friend.domain;

import com.example.todomate_clone.todo.todo.domain.Todo;
import com.example.todomate_clone.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TodoLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @CreatedDate
    private LocalDateTime createdAt;

    public TodoLike(User user, Todo todo) {
        this.user = user;
        this.todo = todo;
    }
}
