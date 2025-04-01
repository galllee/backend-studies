package com.example.miniproject.repository.leave;

import com.example.miniproject.domain.leave.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    public Optional<LeaveBalance> findByMemberIdAndYear(Long memberId, Integer year);
}
