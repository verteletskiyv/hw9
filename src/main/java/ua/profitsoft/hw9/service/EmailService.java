package ua.profitsoft.hw9.service;

import ua.profitsoft.hw9.dto.EmailInfoDto;
import ua.profitsoft.hw9.dto.EmailSendDto;
import ua.profitsoft.hw9.messaging.EmailKafkaMessage;

import java.util.List;

public interface EmailService {
    String saveEmail(EmailKafkaMessage message);
    void sendEmail(String id);
    List<EmailInfoDto> getAll();
    List<EmailInfoDto> getAllWithErrors();
    EmailInfoDto getOne(String id);
    String update(String id, EmailSendDto dto);
    String delete(String id);
}
