package com.example.miniproject.dto.response.commute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WorkTimeResponse {
    private List<DailyWorkResponse> detail;
    private long sum;
}
