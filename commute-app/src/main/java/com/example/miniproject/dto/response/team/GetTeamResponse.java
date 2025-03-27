package com.example.miniproject.dto.response.team;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetTeamResponse {
    private String name;
    private String managerName;
    private int memberCount;
}
