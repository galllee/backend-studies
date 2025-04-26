package com.example.todomate_clone.todo.routine.service;

import com.example.todomate_clone.todo.routine.domain.frequencyDetail.MonthlyFrequencyDetail;
import com.example.todomate_clone.todo.routine.domain.frequencyDetail.WeeklyFrequencyDetail;
import com.example.todomate_clone.todo.routine.domain.frequencyDetail.YearlyFrequencyDetail;
import com.example.todomate_clone.todo.routine.dto.request.CreateTodoFromManualRoutineRequest;
import com.example.todomate_clone.todo.routine.domain.Routine;
import com.example.todomate_clone.todo.routine.dto.request.CreateRoutineRequest;
import com.example.todomate_clone.todo.routine.dto.request.UpdateRoutineRequest;
import com.example.todomate_clone.todo.routine.repository.RoutineRepository;
import com.example.todomate_clone.todo.todo.domain.Todo;
import com.example.todomate_clone.todo.todo.repository.TodoRepository;
import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

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

        ObjectMapper objectMapper = new ObjectMapper();
        List<LocalDate> dates = new ArrayList<>();

        //자동이라면
        if (!request.getIsManual()) {
            int numOfDayBetween = (int) ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
            try {
                switch (request.getFrequency()) {
                    case DAILY -> {
                        dates = IntStream.iterate(0, i -> i + 1).limit(numOfDayBetween)
                                .mapToObj(i -> request.getStartDate().plusDays(i)).collect(Collectors.toList());
                    }
                    case WEEKLY -> {
                        WeeklyFrequencyDetail freqDetail = objectMapper.readValue(request.getFrequencyDetailJson(), WeeklyFrequencyDetail.class);
                        dates = IntStream.iterate(0, i -> i + 1).limit(numOfDayBetween)
                                .mapToObj(i -> request.getStartDate().plusDays(i))
                                .filter(date -> freqDetail.daysOfWeek.contains(date.getDayOfWeek()))
                                .collect(Collectors.toList());
                    }
                    case BIWEEKLY -> {
                        WeeklyFrequencyDetail freqDetail = objectMapper.readValue(request.getFrequencyDetailJson(), WeeklyFrequencyDetail.class);
                        dates = IntStream.iterate(0, i -> i + 1).limit(numOfDayBetween)
                                .mapToObj(i -> request.getStartDate().plusDays(i))
                                .filter(date -> freqDetail.daysOfWeek.contains(date.getDayOfWeek())
                                        && ChronoUnit.WEEKS.between(request.getStartDate(), date) % 2 == 0
                                )
                                .collect(Collectors.toList());
                    }
                    case MONTHLY -> {
                        MonthlyFrequencyDetail freqDetail = objectMapper.readValue(request.getFrequencyDetailJson(), MonthlyFrequencyDetail.class);
                        dates = IntStream.iterate(0, i -> i + 1).limit(numOfDayBetween)
                                .mapToObj(i -> request.getStartDate().plusDays(i))
                                .filter(date -> freqDetail.daysOfMonth.contains(date.getDayOfMonth()))
                                .collect(Collectors.toList());
                    }
                    case YEARLY -> {
                        YearlyFrequencyDetail freqDetail = objectMapper.readValue(request.getFrequencyDetailJson(), YearlyFrequencyDetail.class);
                        dates = IntStream.iterate(0, i -> i + 1).limit(numOfDayBetween)
                                .mapToObj(i -> request.getStartDate().plusDays(i))
                                .filter(date -> freqDetail.daysOfYear.contains(date.getDayOfYear()))
                                .collect(Collectors.toList());
                    }
                }
            } catch(JsonProcessingException e) {
                e.printStackTrace();
                //예외처리 다시
            }

            for(LocalDate date : dates) {
                todoRepository.save(
                        Todo.builder()
                                .categoryId(categoryId)
                                .userId(user.getId())
                                .title(request.getTitle())
                                .date(date)
                                .reminderTime(request.getTime())
                                .build()
                );
            }
        }
    }

    public void updateRoutine(Long routineId, UpdateRoutineRequest request) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new IllegalArgumentException("해당 루틴이 없습니다."));

        routine.updateForm(request);
    }

    public void deleteRoutine(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new IllegalArgumentException("해당 루틴이 없습니다."));

        routineRepository.delete(routine);
    }

    public void createTodoFromManualRoutine(Long routineId, CreateTodoFromManualRoutineRequest request) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new IllegalArgumentException("루틴이 존재하지 않습니다."));

        todoRepository.save(
                Todo.builder()
                        .categoryId(routine.getCategoryId())
                        .userId(routine.getUserId())
                        .title(routine.getTitle())
                        .date(request.getDate())
                        .build()
        );
    }
}
