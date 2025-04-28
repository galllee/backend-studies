package com.example.todomate_clone.notification.controller;

import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.global.util.AuthUtil;
import com.example.todomate_clone.notification.dto.RegisterDeviceTokenRequest;
import com.example.todomate_clone.notification.service.DeviceTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DeviceTokenController {
    private final DeviceTokenService deviceTokenService;

    @PostMapping("/api/v1/device-tokens")
    public ResponseEntity<ApiResponse<?>> registerDeviceToken(@RequestBody RegisterDeviceTokenRequest request) {
        deviceTokenService.registerDeviceToken(AuthUtil.getLoginUsername(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccessWithNoContent("디바이스 토큰 저장 완료"));
    }
}
