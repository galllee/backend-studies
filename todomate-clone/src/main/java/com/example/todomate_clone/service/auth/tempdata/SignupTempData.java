package com.example.todomate_clone.service.auth.tempdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupTempData{
    private String email;
    private String password;
    private boolean agreeToMarketing;

}
