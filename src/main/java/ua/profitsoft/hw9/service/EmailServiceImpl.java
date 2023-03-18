package ua.profitsoft.hw9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.profitsoft.hw9.data.EmailData;
import ua.profitsoft.hw9.data.EmailStatus;
import ua.profitsoft.hw9.dto.EmailInfoDto;
import ua.profitsoft.hw9.dto.EmailSendDto;
import ua.profitsoft.hw9.exception.NotFoundException;
import ua.profitsoft.hw9.messaging.EmailKafkaMessage;
import ua.profitsoft.hw9.repository.EmailsRepository;
import ua.profitsoft.hw9.util.Converter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailsRepository repository;
    private final JavaMailSender mailSender;

    @Override
    public String saveEmail(EmailKafkaMessage kafkaMessage) {
        return repository.save(Converter.toSave(kafkaMessage)).getId();
    }

    @Override
    public void sendEmail(String id) {
        send(getOrThrow(id));
    }

    @Scheduled(cron = "* */5 * * * *")
    public void retrySending() {
        List<EmailData> allWithErrors = repository.findAllByStatus(EmailStatus.ERROR);
        if (allWithErrors.isEmpty()) return;
        allWithErrors.forEach(this::send);
    }

    @Override
    public List<EmailInfoDto> getAll() {
        return repository.findAll().stream().map(Converter::toInfo).toList();
    }

    @Override
    public List<EmailInfoDto> getAllWithErrors() {
        return repository.findAllByStatus(EmailStatus.ERROR).stream().map(Converter::toInfo).toList();
    }

    @Override
    public EmailInfoDto getOne(String id) {
        return Converter.toInfo(getOrThrow(id));
    }

    @Override
    public String update(String id, EmailSendDto dto) {
        EmailData existing = getOrThrow(id);
        existing.setTo(dto.getTo());
        existing.setSubject(dto.getSubject());
        existing.setBody(dto.getBody());
        return repository.save(existing).getId();
    }

    @Override
    public String delete(String id) {
        EmailData found = getOrThrow(id);
        repository.delete(found);
        return found.getId();
    }

    private void send(EmailData emailData) {
        try {
            mailSender.send(Converter.toMailMessage(emailData));
            updStatus(emailData, EmailStatus.SENT, "");
        } catch (MailException e) {
            updStatus(emailData, EmailStatus.ERROR, e.getClass().getName() + "; " + e.getMessage());
        }
    }

    private void updStatus(EmailData emailData, EmailStatus status, String error) {
        emailData.setStatus(status);
        emailData.setErrorMessage(error);
        repository.save(emailData);
    }

    private EmailData getOrThrow(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Failed to load an email. Id: %s".formatted(id)));
    }
}