package com.example.miniproject.service.member;

import com.example.miniproject.domain.member.Member;
import com.example.miniproject.dto.request.member.CreateMemberRequest;
import com.example.miniproject.dto.response.member.GetMemberResponse;
import com.example.miniproject.repository.member.MemberRepository;
import com.example.miniproject.repository.team.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void saveMember(CreateMemberRequest request) {
        memberRepository.save(Member.builder()
                .name(request.getName())
                .role(request.getRole())
                .hireDate(request.getHireDate())
                .birthDate(request.getBirthDate())
                .teamId(request.getTeamId())
                .build());
                //new Member(request.getName(), request.getRole(), request.getHireDate(), request.getBirthDate(), request.getTeamId()));
    }

    @Transactional
    public List<GetMemberResponse> getAllMembers() {
        return memberRepository.findAll().stream().map(m -> new GetMemberResponse(
                m.getName(), teamRepository.findById(m.getTeamId()).orElseThrow().getName(), m.getRole(), m.getBirthDate(), m.getHireDate()
        )).collect(Collectors.toList());
    }
}
