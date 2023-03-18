package ua.profitsoft.hw9.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.profitsoft.hw9.dto.EmailInfoDto;
import ua.profitsoft.hw9.dto.EmailSendDto;
import ua.profitsoft.hw9.dto.RestResponse;
import ua.profitsoft.hw9.messaging.EmailKafkaMessage;
import ua.profitsoft.hw9.service.EmailService;
import ua.profitsoft.hw9.util.Converter;
import java.util.List;
import static ua.profitsoft.hw9.controller.GlobalExceptionHandler.returnErrorsToClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emails")
public class EmailController {

    @Value("${kafka.topic.emails}")
    private String emailsTopic;

    private final KafkaOperations<String, EmailKafkaMessage> kafkaOperations;
    private final EmailService service;


    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse receiveEmail(@Valid @RequestBody EmailSendDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        kafkaOperations.send(emailsTopic, dto.getSubject(), Converter.toKafkaMessage(dto));
        return new RestResponse("Success");
    }

    @GetMapping("/all")
    public List<EmailInfoDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/all/errors")
    public List<EmailInfoDto> getAllWithErrors() {
        return service.getAllWithErrors();
    }

    @GetMapping("/{id}")
    public EmailInfoDto getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public RestResponse update(@PathVariable String id, @Valid @RequestBody EmailSendDto dto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        String updatedId = service.update(id, dto);
        return new RestResponse("Updated. Email id: "+updatedId);
    }

    @DeleteMapping("/{id}")
    public RestResponse delete(@PathVariable String id) {
        String deletedId = service.delete(id);
        return new RestResponse("Deleted. Email id: "+deletedId);
    }
}
