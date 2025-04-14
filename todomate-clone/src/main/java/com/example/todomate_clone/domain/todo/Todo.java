package com.example.todomate_clone.domain.todo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private String memo;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TodoStatus status = TodoStatus.PENDING;

    @Builder
    public Todo(Long categoryId, String title, LocalDate date) {
        this.categoryId = categoryId;
        this.title = title;
        this.date = date;
        this.status = TodoStatus.PENDING;
    }

}
