package com.ecommerce.email;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ecommerce.kafka.order.Product;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 타임리프
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

// 수기 임포트
import static java.nio.charset.StandardCharsets.UTF_8;
import static com.ecommerce.email.EmailTemplates.ORDER_CONFIRMATION;
import static com.ecommerce.email.EmailTemplates.PAYMENT_CONFIRMATION;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(
    		String destinationEmail, String customerName, BigDecimal amount, String orderReference
	) throws MessagingException {
    	
    	// HTML 첨부파일 가능한 메일 객체
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // HTML + 이미지 + 첨부파일 대응, 한글 깨짐 방지
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());

        // 발신자 설정 
        messageHelper.setFrom("asdf@jihun.com");

        // 이메일 템플릿 선택
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        // 템플릿에 전달할 변수 구성
        Map<String, Object> variables = new HashMap <>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        // Thymeleaf Context 생성
        Context context = new Context();
        context.setVariables(variables);
        
        // 메일 제목 설정
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
        	// 템플릿 처리 & 메일 발송
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info("이메일 전송 성공 - 수신자: {}, 템플릿: {}", destinationEmail, templateName);
        } catch (MessagingException e) {
        	log.warn("이메일 전송 실패 - 수신자: {}", destinationEmail);
        }

    }

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail, String customerName, BigDecimal amount, String orderReference, List<Product> products
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("asdf@jihun.com");

        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info("이메일 전송 성공 - 수신자: {}, 템플릿: {}", destinationEmail, templateName);
        } catch (MessagingException e) {
        	log.warn("이메일 전송 실패 - 수신자: {}", destinationEmail);
        }

    }
    
    
    
}
