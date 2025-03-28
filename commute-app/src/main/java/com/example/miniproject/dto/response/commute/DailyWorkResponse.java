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
}
