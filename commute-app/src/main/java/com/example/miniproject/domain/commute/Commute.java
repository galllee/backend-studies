package com.example.miniproject.domain.commute;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
public class Commute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private LocalDate workDate;
    private LocalTime clockIn;

    private LocalTime clockOut;

    public Commute(Long memberId, LocalDate workDate, LocalTime clockIn) {
        this.memberId = memberId;
        this.workDate = workDate;
        this.clockIn = clockIn;
    }

    public void clockOutAt(LocalTime time) {
        clockOut = time;
    }
}
