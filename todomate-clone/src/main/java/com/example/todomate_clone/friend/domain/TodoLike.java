package com.example.todomate_clone.friend.domain;

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

    private Long userId;
    private Long todoId;

    @CreatedDate
    private LocalDateTime createdAt;

    public TodoLike(Long userId, Long todoId) {
        this.userId = userId;
        this.todoId = todoId;
    }
}
