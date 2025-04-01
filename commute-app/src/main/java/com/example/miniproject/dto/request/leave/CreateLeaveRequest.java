package com.example.miniproject.dto.request.leave;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CreateLeaveRequest {
    private Long memberId;
    private LocalDate leaveDate;
}
