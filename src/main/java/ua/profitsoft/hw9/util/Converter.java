package ua.profitsoft.hw9.util;

import org.springframework.mail.SimpleMailMessage;
import ua.profitsoft.hw9.data.EmailData;
import ua.profitsoft.hw9.data.EmailStatus;
import ua.profitsoft.hw9.dto.EmailInfoDto;
import ua.profitsoft.hw9.dto.EmailSendDto;
import ua.profitsoft.hw9.messaging.EmailKafkaMessage;
import java.util.UUID;

public class Converter {
    public static EmailKafkaMessage toKafkaMessage(EmailSendDto dto) {
        return EmailKafkaMessage.builder()
                .to(dto.getTo())
                .subject(dto.getSubject())
                .body(dto.getBody())
                .transactionId(UUID.randomUUID().toString())
                .build();
    }

    public static EmailData toSave(EmailKafkaMessage kafkaMessage) {
        EmailData emailData = new EmailData();
        emailData.addTransactionId(kafkaMessage.getTransactionId());
        emailData.setTo(kafkaMessage.getTo());
        emailData.setSubject(kafkaMessage.getSubject());
        emailData.setBody(kafkaMessage.getBody());
        emailData.setStatus(EmailStatus.NEW);
        return emailData;
    }

    public static SimpleMailMessage toMailMessage(EmailData email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("verteletskiyv@gmail.com");
        message.setTo(email.getTo().toArray(new String[0]));
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        return message;
    }

    public static EmailInfoDto toInfo(EmailData data) {
        return EmailInfoDto.builder()
                .id(data.getId())
                .to(data.getTo())
                .subject(data.getSubject())
                .body(data.getBody())
                .status(data.getStatus())
                .errorMessage(data.getErrorMessage())
                .build();
    }
}
