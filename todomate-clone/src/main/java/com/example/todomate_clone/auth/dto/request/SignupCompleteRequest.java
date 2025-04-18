package com.example.todomate_clone.auth.dto.request;

import com.example.todomate_clone.user.domain.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class SignupCompleteRequest {
    private String token;
    private String name;
    private Gender gender;
    private LocalDate birthDate;
    private String profileImage;
}
