package ua.profitsoft.hw9.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ua.profitsoft.hw9.messaging.EmailKafkaMessage;
import ua.profitsoft.hw9.service.EmailService;

@Component
@RequiredArgsConstructor
public class EmailListener {

    private final EmailService service;

    @KafkaListener(topics = "${kafka.topic.emails}")
    public void emailReceived(EmailKafkaMessage message) {
        String id = service.saveEmail(message);
        service.sendEmail(id);
    }
}
