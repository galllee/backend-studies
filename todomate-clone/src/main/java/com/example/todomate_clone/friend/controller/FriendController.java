package com.example.todomate_clone.friend.controller;

import com.example.todomate_clone.friend.service.FriendService;
import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.global.util.AuthUtil;
import com.example.todomate_clone.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    // {toUserId}를 id로 가진 유저를 팔로우
    @PostMapping("/api/v1/friends/{toUserId}")
    public ResponseEntity<ApiResponse<?>> follow(
            @PathVariable Long toUserId
    ) {
        friendService.follow(AuthUtil.getLoginUsername(), toUserId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("팔로우 완료"));
    }


}
