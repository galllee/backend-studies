package com.example.todomate_clone.todo.category.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private String color;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Category(Long userId, String name, Visibility visibility, String color) {
        this.userId = userId;
        this.name = name;
        this.visibility = visibility;
        this.color = color;
        this.status = CategoryStatus.IN_PROGRESS;
    }

    public void updateCategory(String name, Visibility visibility, String color) {
        this.name = name;
        this.visibility = visibility;
        this.color = color;
    }

    public void completeCategory() {
        this.status = CategoryStatus.COMPLETED;
    }

    public void resumeCategory() {
        this.status = CategoryStatus.IN_PROGRESS;
    }
}
