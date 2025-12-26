package com.ecommerce.notification;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 수기 임포트
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

	private final KafkaTemplate<String, PaymentNotificationRequest> kafkaTemplate;
	
	public void sendNotification(PaymentNotificationRequest request) {
		log.info("본문에 알림 전송 = < {} >", request);
		Message<PaymentNotificationRequest> message = MessageBuilder
				.withPayload(request)
				.setHeader(TOPIC, "payment-topic")
				.build();
	
		kafkaTemplate.send(message);
	}
	
}
