package com.example.miniproject.dto.request.commute;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class ClockInRequest {
    private Long memberId;
    private LocalDate workDate;
    private LocalTime clockIn;
}
