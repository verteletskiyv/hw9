package ua.profitsoft.hw9.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Builder
@Jacksonized
public class EmailKafkaMessage {
    private String transactionId;
    private String from;
    private List<String> to;
    private String subject;
    private String body;
}
