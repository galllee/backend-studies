package com.example.todomate_clone.dto.request.user;

import com.example.todomate_clone.domain.user.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
    private Gender gender;
    private LocalDate birthDate;
    private String profileImage;
}
