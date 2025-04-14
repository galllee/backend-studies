package com.example.todomate_clone.service.user;

import com.example.todomate_clone.domain.user.User;
import com.example.todomate_clone.dto.request.user.CreateUserRequest;
import com.example.todomate_clone.repository.user.UserRepository;
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
