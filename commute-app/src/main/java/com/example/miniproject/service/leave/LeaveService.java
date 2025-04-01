package com.example.miniproject.service.leave;

import com.example.miniproject.domain.leave.LeaveBalance;
import com.example.miniproject.domain.leave.LeaveRequest;
import com.example.miniproject.dto.request.leave.CreateLeaveRequest;
import com.example.miniproject.repository.leave.LeaveBalanceRepository;
import com.example.miniproject.repository.leave.LeaveRequestRepository;
import com.example.miniproject.repository.member.MemberRepository;
import com.example.miniproject.repository.team.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Service
public class LeaveService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    private boolean isLeaveRequestAvailable(int teamLeaveNoticeDays, LocalDate leaveDate) {
        return teamLeaveNoticeDays <= ChronoUnit.DAYS.between(LocalDate.now(), leaveDate);
    }

    @Transactional
    public void leaveRequest(CreateLeaveRequest request) {
        Long memberId = request.getMemberId();
        Long teamId = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."))
                .getTeamId();
        int teamLeaveNoticeDays = teamRepository.findById(teamId)
                .orElseThrow(()-> new IllegalArgumentException("사용자가 팀에 속해있지 않습니다."))
                .getLeaveNoticeDays();

        if (request.getLeaveDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("이미 지난 날짜에는 연차를 신청할 수 없습니다.");
        }
        if (!isLeaveRequestAvailable(teamLeaveNoticeDays, request.getLeaveDate())) {
            throw new IllegalArgumentException("연차는 최소" + teamLeaveNoticeDays + "일 전에 신청해야 합니다");
        }

        leaveRequestRepository.save(new LeaveRequest(request.getMemberId(), LocalDate.now(), request.getLeaveDate()));

        LeaveBalance leaveBalance = leaveBalanceRepository.findByMemberIdAndYear(request.getMemberId(), request.getLeaveDate().getYear())
                .orElseThrow(() -> new IllegalArgumentException("연차 정보가 존재하지 않습니다."));
        leaveBalance.useLeave();
        leaveBalance.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public int getRemainingLeaves(Long memberId) {
        return leaveBalanceRepository.findByMemberIdAndYear(memberId, LocalDate.now().getYear())
                .orElseThrow(() -> new IllegalArgumentException("남은 연차 정보가 없습니다."))
                .getRemainingDays();
    }
}
