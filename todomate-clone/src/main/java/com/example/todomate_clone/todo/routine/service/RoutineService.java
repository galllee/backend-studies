package com.example.todomate_clone.todo.routine.service;

import com.example.todomate_clone.todo.routine.domain.Routine;
import com.example.todomate_clone.todo.routine.dto.request.CreateRoutineRequest;
import com.example.todomate_clone.todo.routine.repository.RoutineRepository;
import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createRoutine(String email, Long categoryId, CreateRoutineRequest request) {
        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        routineRepository.save(
                Routine.builder()
                        .userId(user.getId())
                        .categoryId(categoryId)
                        .title(request.getTitle())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .frequency(request.getFrequency())
                        .frequencyDetailJson(request.getFrequencyDetailJson())
                        .time(request.getTime())
                        .isManual(request.getIsManual())
                        .build()
        );
    }

}
