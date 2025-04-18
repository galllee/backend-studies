package com.example.todomate_clone.auth.service;

import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    @Transactional
    public void saveRefreshToken(String email, String refreshToken) {
        userRepository.findByEmail(email).ifPresent(
                user -> user.updateRefreshToken(refreshToken)
        );
    }
}
