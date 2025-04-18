package com.example.todomate_clone.auth.service;

import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.auth.dto.request.SignupCompleteRequest;
import com.example.todomate_clone.auth.dto.request.SignupRequest;
import com.example.todomate_clone.global.exception.CustomException;
import com.example.todomate_clone.user.repository.UserRepository;
import com.example.todomate_clone.auth.service.store.SignupMemoryStore;
import com.example.todomate_clone.auth.service.tempdata.SignupTempData;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class SignupService {
    private final UserRepository userRepository;
    private final SignupMemoryStore signupMemoryStore;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void signupRequest(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("이미 사용 중인 이메일입니다.");
        }
        String token = UUID.randomUUID().toString();
        signupMemoryStore.save(token,
                SignupTempData.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode((request.getPassword())))
                        .agreeToMarketing(request.isAgreeToMarketing())
                        .build()
                );

        emailService.sendVerificationEmail(request.getEmail(), token);
    }

    public void signupVerify(String token) {
        SignupTempData tempData = signupMemoryStore.get(token);
        if (tempData == null) {
            throw new CustomException("유효하지 않은 토큰입니다.");
        }

        tempData.markVerified();
    }

    public void signupComplete (SignupCompleteRequest request) {
        SignupTempData tempData = signupMemoryStore.get(request.getToken());
        if (tempData == null || !tempData.isVerified()) {
            throw new CustomException("이메일 인증을 완료해주세요.");
        }

        userRepository.save(
                User.builder()
                        .name(request.getName())
                        .email(tempData.getEmail())
                        .password(tempData.getPassword())
                        .gender(request.getGender())
                        .birthDate(request.getBirthDate())
                        .profileImage(request.getProfileImage())
                        .build()
        );

        signupMemoryStore.remove(request.getToken());
    }
}
