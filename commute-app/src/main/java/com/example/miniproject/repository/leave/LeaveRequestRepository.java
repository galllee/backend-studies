package com.example.miniproject.repository.leave;

import com.example.miniproject.domain.leave.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    public Optional<LeaveRequest> findByMemberIdAndLeaveDate(Long memberId, LocalDate leaveDate);
    public List<LeaveRequest> findAllByMemberId(Long memberId);
}
