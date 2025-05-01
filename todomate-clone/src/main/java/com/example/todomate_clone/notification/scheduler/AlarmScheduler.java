package com.example.todomate_clone.notification.scheduler;

import com.example.todomate_clone.notification.domain.DeviceToken;
import com.example.todomate_clone.notification.repository.DeviceTokenRepository;
import com.example.todomate_clone.notification.service.FirebaseCloudMessageService;
import com.example.todomate_clone.todo.todo.domain.Todo;
import com.example.todomate_clone.todo.todo.repository.TodoRepository;
import com.google.firebase.internal.FirebaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AlarmScheduler {
    private final TodoRepository todoRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final DeviceTokenRepository deviceTokenRepository;

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void checkAndSendTodoAlarms() {
        System.out.println("함수 호출은 되고 있습니다.");
        List<Todo> todos = todoRepository.findAllByReminderTimeLessThanEqualAndReminderSentFalse(LocalTime.now());

        for(Todo todo : todos) {
            List<DeviceToken> tokens = deviceTokenRepository.findAllByUser(todo.getUser());
            List<String> tokenStrings = tokens.stream()
                    .map(DeviceToken::getDeviceToken)
                    .collect(Collectors.toList());

            for (String tokenString : tokenStrings) {
                try {
                    System.out.println("알림 발송 시도 - Token: " + tokenString + ", Todo Title: " + todo.getTitle());
                    firebaseCloudMessageService.sendMessageTo(
                            tokenString,
                            "reminder",
                            todo.getTitle()
                    );
                    todo.markReminderSent();
                } catch (Exception e) {
                    System.out.println("알림 발송 실패: " + e.getMessage());
                }
            }

        }
    }
}
