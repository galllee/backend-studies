package com.example.todomate_clone.service.auth;

import com.example.todomate_clone.domain.user.User;
import com.example.todomate_clone.dto.request.auth.SigninRequest;
import com.example.todomate_clone.exception.CustomException;
import com.example.todomate_clone.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
