package com.example.todomate_clone.auth.service;

import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.auth.dto.request.SigninRequest;
import com.example.todomate_clone.global.exception.CustomException;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SigninService {
    private final UserRepository userRepository;



    public User signin(SigninRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("Couldn't find user using the email address"));

        if(!user.getPassword().equals(request.getPassword())) {
            throw new CustomException("Wrong password");
        }

        return user;
    }
}
