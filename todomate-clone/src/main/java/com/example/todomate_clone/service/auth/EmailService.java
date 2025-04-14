package com.example.todomate_clone.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String token) {
        String link = "http://localhost:8080/api/v1/signup/complete?token=" + token;
        String subject = "TodoMate 이메일 인증";
        String body = "환영합니다!\n 아래 링크를 클릭해서 이메일 인증을 완료해주세요:\n\n" + link;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
