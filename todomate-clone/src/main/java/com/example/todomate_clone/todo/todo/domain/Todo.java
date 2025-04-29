package com.example.todomate_clone.todo.todo.domain;


import com.example.todomate_clone.todo.category.domain.Category;
import com.example.todomate_clone.user.domain.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    private String title;
    private LocalDate date; //scheduledDate로 바꿔주기?

    @Enumerated(EnumType.STRING)
    private TodoStatus status = TodoStatus.PENDING;

    private String memo;
    private boolean isMemoPrivate;
    private LocalTime reminderTime;
    private boolean reminderSent;
    private Long elapsedTime;
    private boolean isArchived;
    private Long orderNum;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Todo(Category category, User user, String title, LocalDate date, LocalTime reminderTime) {
        this.category = category;
        this.user = user;
        this.title = title;
        this.date = date;
        this.reminderTime = reminderTime;
        this.reminderSent = false;
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

    public void resetElapsedTime() {
        this.elapsedTime = 0L;
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

    public void updateOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void markReminderSent() {
        this.reminderSent = true;
    }

    public Todo repeatForDate(LocalDate newDate) {
        return Todo.builder()
                .category(this.category)
                .user(this.user)
                .title(this.title)
                .date(newDate)
                .reminderTime(this.reminderTime).build();
    }

    public static Todo createFromRoutine(
            User user,
            Category category,
            String title,
            LocalDate date,
            LocalTime reminderTime
    ) {
        return Todo.builder()
                .user(user)
                .category(category)
                .title(title)
                .date(date)
                .reminderTime(reminderTime).build();
    }
}
