package com.example.miniproject.dto.request.commute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;

@NoArgsConstructor
@Getter
@Setter
public class WorkTimeRequest {
    private Long memberId;
    private YearMonth yearMonth;
}

