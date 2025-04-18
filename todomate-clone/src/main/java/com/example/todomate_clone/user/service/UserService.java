package com.example.todomate_clone.user.service;

import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.dto.request.CreateUserRequest;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void createUser(CreateUserRequest request) {

        userRepository.save(
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .gender(request.getGender())
                        .birthDate(request.getBirthDate())
                        .profileImage(request.getProfileImage())
                        .build()
        );

    }


}
