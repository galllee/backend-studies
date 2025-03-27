package com.example.miniproject.repository.member;

import com.example.miniproject.domain.member.Member;
import com.example.miniproject.domain.member.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByTeamIdAndRole(Long teamId, Role role);
    public int countByTeamId(Long teamId);
}
