package com.example.miniproject.service.commute;

import com.example.miniproject.domain.commute.Commute;
import com.example.miniproject.domain.leave.LeaveRequest;
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

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Service
public class WorkTimeService {
    private final CommuteRepository commuteRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final MemberRepository memberRepository;

//    private boolean isOnLeave(Long memberId, LocalDate date) {
//        return leaveRequestRepository.findByMemberIdAndLeaveDate(memberId, date).isPresent();
//    }

    private List<DailyWorkResponse> personalMonthWorkTime(Long memberId, YearMonth yearMonth) {
        return commuteRepository.findAllByMemberId(memberId)
                .stream()
                .filter(
                        commute -> YearMonth.from(commute.getWorkDate()).equals(yearMonth)
                )
                .map(commute -> new DailyWorkResponse(commute.getWorkDate(), Duration.between(commute.getClockIn(), commute.getClockOut()).toMinutes()))
                .collect(Collectors.toList());
    }
    private List<LocalDate> getAllDatesOfMonth(YearMonth yearMonth) {
        return IntStream.rangeClosed(1, yearMonth.lengthOfMonth())
                .mapToObj(yearMonth::atDay)
                .collect(Collectors.toList());
    }

    private DailyWorkResponse toDailyResponse(LocalDate date,
                                              Map<LocalDate, Commute> commuteMap,
                                              Map<LocalDate, LeaveRequest> leaveMap) {
        if (leaveMap.containsKey(date)) {
            return new DailyWorkResponse(date, 0, true);
        }

        Commute commute = commuteMap.get(date);
        long minutes = (commute != null)
                ? Duration.between(commuteMap.get(date).getClockIn(), commuteMap.get(date).getClockOut()).toMinutes()
                : 0;

        return new DailyWorkResponse(date, minutes, false);
    }

    private Map<LocalDate, Commute> getCommuteMap(Long memberId) {
        return commuteRepository.findAllByMemberId(memberId)
                .stream()
                .collect(Collectors.toMap(Commute::getWorkDate, Function.identity()));
    }

    private Map<LocalDate, LeaveRequest> getLeaveMap(Long memberId) {
        return leaveRequestRepository.findAllByMemberId(memberId)
                .stream()
                .collect(Collectors.toMap(LeaveRequest::getLeaveDate, Function.identity()));
    }

    private long calculateTotalMinutes(List<DailyWorkResponse> list) {
        return list.stream().mapToLong(DailyWorkResponse::getWorkingMinutes).sum();
    }

    private long countWeekendsInMonth(YearMonth yearMonth) {
        return IntStream.rangeClosed(1, yearMonth.lengthOfMonth())
                .mapToObj(yearMonth::atDay)
                .filter(date -> {
                    DayOfWeek day = date.getDayOfWeek();
                    return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
                })
                .count();
    }

    @Transactional
    public WorkTimeResponse getWorkTime(WorkTimeRequest request) {
        List<DailyWorkResponse> personalDailyWorkTimes = personalMonthWorkTime(request.getMemberId(), request.getYearMonth());

        long sum = personalDailyWorkTimes.stream().mapToLong(DailyWorkResponse::getWorkingMinutes).sum();

        return new WorkTimeResponse(personalDailyWorkTimes, sum);
    }

    @Transactional
    public WorkTimeResponse getWorkTimeV2(WorkTimeRequest request) {
        Long memberId = request.getMemberId();
        YearMonth yearMonth = request.getYearMonth();

        List<LocalDate> allDates = getAllDatesOfMonth(yearMonth);
        Map<LocalDate, Commute> commuteMap = getCommuteMap(memberId);
        Map<LocalDate, LeaveRequest> leaveMap = getLeaveMap(memberId);

        List<DailyWorkResponse> dailyWorkResponses = allDates.stream()
                .map(date -> toDailyResponse(date, commuteMap, leaveMap))
                .collect(Collectors.toList());

        long totalMinutes = calculateTotalMinutes(dailyWorkResponses);

        return new WorkTimeResponse(dailyWorkResponses, totalMinutes);
    }

    @Transactional
    public List<OverTimeResponse> getOvertime(YearMonth yearMonth) {
        long workLimit = 8 * 60 * (yearMonth.lengthOfMonth() - countWeekendsInMonth(yearMonth));
        //공휴일 추가: 내가 직접 넣어주거나 공공데이터 쓰기
        return memberRepository.findAll().stream().map(
                member -> {
                    long difference = calculateTotalMinutes(personalMonthWorkTime(member.getId(), yearMonth)) - workLimit;
                    if (difference < 0) {
                        return new OverTimeResponse(member.getId(), member.getName(), 0);
                    } else {
                        return new OverTimeResponse(member.getId(), member.getName(), difference);
                    }
                }).collect(Collectors.toList());
    }
}
