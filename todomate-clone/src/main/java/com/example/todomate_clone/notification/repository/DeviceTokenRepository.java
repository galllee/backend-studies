package com.example.todomate_clone.notification.repository;

import com.example.todomate_clone.notification.domain.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
    public List<DeviceToken> findAllByUserId(Long userId);
}
