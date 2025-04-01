package com.example.miniproject.domain.leave;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private LocalDate requestDate;
    private LocalDate leaveDate;

    public LeaveRequest(Long memberId, LocalDate requestDate, LocalDate leaveDate) {
        this.memberId = memberId;
        this.requestDate = requestDate;
        this.leaveDate = leaveDate;
    }
}
