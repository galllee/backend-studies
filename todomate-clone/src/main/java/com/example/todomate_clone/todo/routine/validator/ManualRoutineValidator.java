package com.example.todomate_clone.todo.routine.validator;

import com.example.todomate_clone.todo.routine.dto.request.CreateRoutineRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ManualRoutineValidator implements ConstraintValidator<RequireDateIfNotManual, CreateRoutineRequest> {

    @Override
    public boolean isValid(CreateRoutineRequest request, ConstraintValidatorContext context) {
        if (Boolean.FALSE.equals(request.getIsManual())) {
            if (request.getStartDate() == null || request.getEndDate() == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("자동 루틴은 시작일과 종료일이 필요합니다.")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
