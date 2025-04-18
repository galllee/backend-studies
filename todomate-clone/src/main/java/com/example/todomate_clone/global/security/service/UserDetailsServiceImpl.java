package com.example.todomate_clone.global.security.service;

import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import com.example.todomate_clone.global.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(email));

        return new UserDetailsImpl(user);
    }

}
