package com.example.todomate_clone.notification.dto;

import lombok.Getter;

@Getter
public class TestAlarmRequest {
    private String targetToken;
    private String title;
    private String body;
}
