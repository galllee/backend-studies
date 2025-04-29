package com.example.todomate_clone.notification.repository;

import com.example.todomate_clone.notification.domain.DeviceToken;
import com.example.todomate_clone.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
    public List<DeviceToken> findAllByUser(User user);
}
