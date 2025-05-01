package com.example.todomate_clone.notification.event;

import com.example.todomate_clone.friend.domain.Friend;
import com.example.todomate_clone.friend.repository.FriendRepository;
import com.example.todomate_clone.notification.domain.DeviceToken;
import com.example.todomate_clone.notification.repository.DeviceTokenRepository;
import com.example.todomate_clone.notification.service.FirebaseCloudMessageService;
import com.example.todomate_clone.todo.todo.domain.Todo;
import com.example.todomate_clone.user.domain.User;
import com.google.firebase.auth.FirebaseAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TodoCompletedEventListener {
    private final FriendRepository friendRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final DeviceTokenRepository deviceTokenRepository;

    @EventListener
    public void handleTodoCompleted(TodoCompletedEvent event) {
        User user = event.getUser();
        List<User> followers = friendRepository.findAllByToUserId(user.getId());

        for (User follower : followers) {
            List<DeviceToken> tokens = deviceTokenRepository.findAllByUser(follower);
            List<String> tokenStrings = tokens.stream()
                            .map(DeviceToken::getDeviceToken)
                                    .collect(Collectors.toList());

            for(String tokenString : tokenStrings) {
                try {
                    System.out.println("투두 완료 알림 발송 시도 - Token: " + tokenString);
                    firebaseCloudMessageService.sendMessageTo(
                            tokenString,
                            "할 일 완료 알림",
                            event.getUser().getName() + "님이 " + event.getTodo().getTitle() + "을 완료했습니다."
                    );
                } catch (Exception e) {
                    System.out.println("알림 발송 실패: " + e.getMessage());
                }
            }
        }
    }
}
