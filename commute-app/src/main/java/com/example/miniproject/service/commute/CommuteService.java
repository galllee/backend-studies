package com.example.miniproject.service.commute;

import com.example.miniproject.domain.commute.Commute;
import com.example.miniproject.dto.request.commute.ClockInRequest;
import com.example.miniproject.dto.request.commute.WorkTimeRequest;
import com.example.miniproject.dto.response.commute.DailyWorkResponse;
import com.example.miniproject.dto.response.commute.WorkTimeResponse;
import com.example.miniproject.repository.commute.CommuteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommuteService {

    private final CommuteRepository commuteRepository;

    @Transactional
    public void clockIn(ClockInRequest request) {
        commuteRepository.save(new Commute(
                request.getMemberId(),
                request.getWorkDate(),
                request.getClockIn()));
    }

    @Transactional
    public void clockOut(Long memberId) {
        commuteRepository.findById(memberId).ifPresent(commute -> {
            commute.clockOutAt(LocalTime.now());
        });
    }

    @Transactional
    public WorkTimeResponse getWorkTime(WorkTimeRequest request) {
        List<DailyWorkResponse> personalDailyWorkTime = commuteRepository.findAllByMemberId(request.getMemberId())
                .stream()
                .filter(
                        commute -> YearMonth.from(commute.getWorkDate()).equals(request.getYearMonth())
                )
                .map(commute -> new DailyWorkResponse(commute.getWorkDate(), Duration.between(commute.getClockIn(), commute.getClockOut()).toMinutes()))
                .collect(Collectors.toList());

        long sum = personalDailyWorkTime.stream().mapToLong(DailyWorkResponse::getWorkingMinutes).sum();

        return new WorkTimeResponse(personalDailyWorkTime, sum);
    }
}
