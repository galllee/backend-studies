package com.example.miniproject.service.team;

import com.example.miniproject.domain.member.Member;
import com.example.miniproject.domain.member.Role;
import com.example.miniproject.domain.team.Team;
import com.example.miniproject.dto.request.team.CreateTeamRequest;
import com.example.miniproject.dto.response.team.GetTeamResponse;
import com.example.miniproject.repository.member.MemberRepository;
import com.example.miniproject.repository.team.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveTeam(CreateTeamRequest request) {
        teamRepository.save(new Team(request.getName()));
    }

    @Transactional
    public List<GetTeamResponse> getAllTeams() {
        return teamRepository.findAll().stream().map(t -> new GetTeamResponse(
                t.getName(),
                memberRepository.findByTeamIdAndRole(t.getId(), Role.MANAGER).map(Member::getName).orElse(null),
                memberRepository.countByTeamId(t.getId())
        )).collect(Collectors.toList());
    }
}
