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
    private final MemberRepository memberRepository;

    @Transactional
    public void clockIn(ClockInRequest request) {
        if (!memberRepository.existsById(request.getMemberId())) {
            throw new IllegalArgumentException("등록되지 않은 직원입니다.");
        }
        if (commuteRepository.existsByMemberIdAndClockOutIsNull(request.getMemberId())) {
            throw new IllegalArgumentException("이미 출근 중입니다. 퇴근 후 다시 시도하세요.");
        }
        commuteRepository.save(new Commute(
                request.getMemberId(),
                request.getWorkDate(),
                request.getClockIn()));
    }

    @Transactional
    public void clockOut(Long memberId) {
        if (!commuteRepository.existsByMemberIdAndClockOutIsNotNull(memberId)) {
            throw new IllegalArgumentException("출근하지 않았거나 이미 퇴근한 직원입니다.");
        }
        commuteRepository.findByMemberIdAndClockOutIsNull(memberId).ifPresent(commute -> {
            commute.clockOutAt(LocalTime.now());
        });
    }



}
