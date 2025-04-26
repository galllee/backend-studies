package com.example.todomate_clone.todo.todo.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long categoryId;
    private Long userId;

    private String title;
    private LocalDate date; //scheduledDate로 바꿔주기?

    @Enumerated(EnumType.STRING)
    private TodoStatus status = TodoStatus.PENDING;

    private String memo;
    private boolean isMemoPrivate;
    private LocalTime reminderTime;
    private Long elapsedTime;
    private boolean isArchived;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Todo(Long categoryId, Long userId, String title, LocalDate date, LocalTime reminderTime) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.title = title;
        this.date = date;
        this.reminderTime = reminderTime;
        this.status = TodoStatus.PENDING;
        this.isArchived = false;
    }

    public void editTodo(String title) {
        this.title = title;
    }

    public void updateTodoMemo(String memo, boolean isMemoPrivate) {
        this.memo = memo;
        this.isMemoPrivate = isMemoPrivate;
    }

    public void updateTodoReminderTime(LocalTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public void updateElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void markAsCompleted() {
        this.status = TodoStatus.COMPLETED;
    }

    public void updateDate(LocalDate date) {
        this.date = date;
    }

    public void archive() {
        this.isArchived = true;
    }
}
