package com.example.miniproject.repository.commute;

import com.example.miniproject.domain.commute.Commute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommuteRepository extends JpaRepository<Commute, Long> {
    public List<Commute> findAllByMemberId(Long memberId);
}
