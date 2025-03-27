package com.example.miniproject.dto.response.member;

import com.example.miniproject.domain.member.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class GetMemberResponse {
    private String name;
    private String teamName;
    private Role role;
    private LocalDate birthDate;
    private LocalDate workStartDate;
}
