package com.example.miniproject.dto.request.member;

import com.example.miniproject.domain.member.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CreateMemberRequest {
    private String name;
    private Role role;
    private LocalDate hireDate;
    private LocalDate birthDate;
    private Long teamId;
}
