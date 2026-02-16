package com.ecommerce.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ecommerce.auth.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(User user) {
        String subject = "이메일 인증 안내";
        String content = """
                안녕하세요 %s 님,
                
                인증 코드는 다음과 같습니다:
                %s
                
                해당 코드를 입력하여 이메일 인증을 완료해주세요.
                
                감사합니다.
                """.formatted(
                user.getUsername(),
                user.getVerificationCode()
        );
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(user.getEmail());
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);

        } catch (Exception e) {
            throw new IllegalStateException("이메일 전송에 실패했습니다.", e);
        }
    }
	
    
}
