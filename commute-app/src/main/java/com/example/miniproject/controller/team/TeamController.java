package com.example.miniproject.controller.team;

import com.example.miniproject.domain.team.Team;
import com.example.miniproject.dto.request.team.CreateTeamRequest;
import com.example.miniproject.dto.response.team.GetTeamResponse;
import com.example.miniproject.service.team.TeamService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/api/v1/team")
    public void saveTeam(@RequestBody CreateTeamRequest request) {
        teamService.saveTeam(request);
    }

    @GetMapping("/api/v1/team")
    public List<GetTeamResponse> getAllTeams() {
        return teamService.getAllTeams();
    }
}
