package com.example.todomate_clone.todo.category.domain;

import com.example.todomate_clone.user.domain.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private String color;

    private Long orderNum;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Category(User user, String name, Visibility visibility, String color) {
        this.user = user;
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

    public void updateOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }
}
