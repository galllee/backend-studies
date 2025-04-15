package com.example.todomate_clone.service.auth;

import com.example.todomate_clone.domain.user.User;
import com.example.todomate_clone.dto.request.auth.SignupCompleteRequest;
import com.example.todomate_clone.dto.request.auth.SignupRequest;
import com.example.todomate_clone.exception.CustomException;
import com.example.todomate_clone.repository.user.UserRepository;
import com.example.todomate_clone.service.auth.store.SignupMemoryStore;
import com.example.todomate_clone.service.auth.tempdata.SignupTempData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class SignupService {
    private final UserRepository userRepository;
    private final SignupMemoryStore signupMemoryStore;
    private final EmailService emailService;

    public void signupRequest(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("이미 사용 중인 이메일입니다.");
        }
        String token = UUID.randomUUID().toString();
        signupMemoryStore.save(token,
                SignupTempData.builder()
                        .email(request.getEmail())
                        .password(request.getPassword())
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
