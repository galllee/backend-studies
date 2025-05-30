package com.example.todomate_clone.todo.routine.domain;

import com.example.todomate_clone.todo.category.domain.Category;
import com.example.todomate_clone.todo.routine.dto.request.UpdateRoutineRequest;
import com.example.todomate_clone.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
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
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String title;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;
    private String frequencyDetailJson;

    private LocalTime time;

    @Column(nullable = false)
    private boolean isManual;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Routine(User user, Category category, String title,
                   LocalDate startDate, LocalDate endDate,
                   Frequency frequency, String frequencyDetailJson,
                   LocalTime time, boolean isManual) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
        this.frequencyDetailJson = frequencyDetailJson;
        this.time = time;
        this.isManual = isManual;
    }

    public void updateForm(UpdateRoutineRequest request) {
        this.title = request.getTitle();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.frequency = request.getFrequency();
        this.frequencyDetailJson = request.getFrequencyDetailJson();
        this.time = request.getTime();
        this.isManual = request.getIsManual();
    }
}
