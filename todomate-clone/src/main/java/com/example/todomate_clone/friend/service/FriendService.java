package com.example.todomate_clone.friend.service;

import com.example.todomate_clone.friend.domain.Friend;
import com.example.todomate_clone.friend.repository.FriendRepository;
import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import com.example.todomate_clone.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    //public void follow(Long fromUserId, Long toUserId) {

    @Transactional
    public void follow(String fromUserName, Long toUserId) {
        User user = userRepository.findByEmail(fromUserName)
                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        friendRepository.save(
                new Friend(user.getId(), toUserId)
        );
    }



}
