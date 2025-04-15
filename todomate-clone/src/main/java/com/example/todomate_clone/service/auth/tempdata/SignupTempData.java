package com.example.todomate_clone.service.auth.tempdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupTempData{
    private String email;
    private String password;
    private boolean agreeToMarketing;
    private boolean isVerified;

    @Builder
    public SignupTempData(String email, String password, boolean agreeToMarketing) {
        this.email = email;
        this.password = password;
        this.agreeToMarketing = agreeToMarketing;
        this.isVerified = false;
    }

    public void markVerified() {
        this.isVerified = true;
    }
}
