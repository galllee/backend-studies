package com.example.miniproject.service.commute;

import com.example.miniproject.domain.commute.Commute;
import com.example.miniproject.domain.leave.LeaveRequest;
import com.example.miniproject.dto.request.commute.ClockInRequest;
import com.example.miniproject.dto.request.commute.WorkTimeRequest;
import com.example.miniproject.dto.response.commute.DailyWorkResponse;
import com.example.miniproject.dto.response.commute.OverTimeResponse;
import com.example.miniproject.dto.response.commute.WorkTimeResponse;
import com.example.miniproject.repository.commute.CommuteRepository;
import com.example.miniproject.repository.leave.LeaveRequestRepository;
import com.example.miniproject.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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



}
