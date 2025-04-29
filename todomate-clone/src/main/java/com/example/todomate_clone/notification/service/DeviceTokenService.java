package com.example.todomate_clone.notification.service;

import com.example.todomate_clone.notification.domain.DeviceToken;
import com.example.todomate_clone.notification.dto.RegisterDeviceTokenRequest;
import com.example.todomate_clone.notification.repository.DeviceTokenRepository;
import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeviceTokenService {
    private final DeviceTokenRepository deviceTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public void registerDeviceToken(Long id, RegisterDeviceTokenRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        deviceTokenRepository.save(
                new DeviceToken(user, request.getDeviceToken())
        );
    }

}
