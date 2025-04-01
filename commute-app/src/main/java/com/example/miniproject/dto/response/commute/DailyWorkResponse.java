package com.example.miniproject.dto.response.commute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DailyWorkResponse {
    private LocalDate date;
    private long workingMinutes;
    private boolean usingLeave;

    public DailyWorkResponse(LocalDate workDate, long minutes) {
        this.date = workDate;
        this.workingMinutes = minutes;
    }
}
