package com.example.todomate_clone.friend.service;

import com.example.todomate_clone.friend.domain.Friend;
import com.example.todomate_clone.friend.repository.FriendRepository;
import com.example.todomate_clone.user.domain.User;
import com.example.todomate_clone.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        User toUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        friendRepository.save(
                new Friend(fromUser, toUser)
        );
    }

    @Transactional
    public void unfollow(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        Friend friend = friendRepository.findByFromUserIdAndToUserId(
                fromUser.getId(), toUserId
        ).orElseThrow(() -> new IllegalArgumentException("해당 팔로우 목록이 없습니다."));

        friendRepository.delete(friend);
    }


}
