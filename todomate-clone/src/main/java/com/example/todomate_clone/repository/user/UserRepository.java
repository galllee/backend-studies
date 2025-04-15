package com.example.todomate_clone.repository.user;

import com.example.todomate_clone.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByEmail(String email);
    public Optional<User> findByEmail(String email);
}
