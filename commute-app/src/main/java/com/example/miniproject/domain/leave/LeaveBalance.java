package com.example.miniproject.domain.leave;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class LeaveBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private int totalDays;
    private int usedDays;
    private int year;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public int getRemainingDays() {
        return totalDays - usedDays;
    }

    public void useLeave() {
        usedDays++;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}