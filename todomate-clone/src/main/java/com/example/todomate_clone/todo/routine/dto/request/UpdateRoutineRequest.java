package com.example.todomate_clone.todo.routine.dto.request;

import com.example.todomate_clone.todo.routine.domain.Frequency;
import com.example.todomate_clone.todo.routine.validator.RequireDateIfNotManual;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@RequireDateIfNotManual
public class UpdateRoutineRequest {
    @NotBlank(message = "이름은 필수입니다.")
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Frequency frequency;
    private String frequencyDetailJson;
    private LocalTime time;
    @NotNull(message = "자동/수동 설정을 해주십시오.")
    private Boolean isManual;
}
