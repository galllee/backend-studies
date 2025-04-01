package com.example.miniproject.dto.response.commute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OverTimeResponse {
    private Long id;
    private String name;
    private long overtimeMinutes;
}
