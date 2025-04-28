package com.example.todomate_clone.notification.controller;

import com.example.todomate_clone.global.response.ApiResponse;
import com.example.todomate_clone.notification.dto.TestAlarmRequest;
import com.example.todomate_clone.notification.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.RequestDate;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FirebaseCloudMessageController {
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/api/v1/fcm")
    public ResponseEntity<ApiResponse<?>> pushMessage(@RequestBody TestAlarmRequest request) throws IOException {
        System.out.println(request.getTargetToken() + " " + request.getTitle() + " " + request.getBody());
        firebaseCloudMessageService.sendMessageTo(
                request.getTargetToken(),
                request.getTitle(),
                request.getBody()
        );
        return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent("메세지 전송 완료"));
    }

}
