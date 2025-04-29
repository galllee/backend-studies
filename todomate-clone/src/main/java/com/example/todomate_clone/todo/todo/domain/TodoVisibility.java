package com.example.todomate_clone.todo.todo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TodoVisibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long todoId;
    private Long userId;

    @CreatedDate
    private LocalDateTime createdAt;

    public TodoVisibility(Long todoId, Long userId) {
        this.todoId = todoId;
        this.userId = userId;
    }
}
