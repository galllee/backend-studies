package com.example.todomate_clone.notification.dto;

import lombok.Getter;

@Getter
public class NewsNotificationRequest {
    private String targetToken;
    private String title;
    private String body;
}
