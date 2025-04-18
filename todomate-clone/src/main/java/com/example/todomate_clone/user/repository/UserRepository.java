package com.example.todomate_clone.user.repository;

import com.example.todomate_clone.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByEmail(String email);
    public Optional<User> findByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
}
