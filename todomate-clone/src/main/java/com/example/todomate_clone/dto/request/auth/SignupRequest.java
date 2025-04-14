package com.example.todomate_clone.dto.request.auth;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignupRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "weak password: 비밀번호는 8~20자여야 합니다.")
    private String password;

    @AssertTrue(message = "14세 이상만 가입할 수 있습니다.")
    private boolean agreeToAgeLimit;

    private boolean agreeToMarketing;
}
