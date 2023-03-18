package ua.profitsoft.hw9.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import ua.profitsoft.hw9.data.EmailStatus;

import java.util.List;

@Getter
@Builder
@Jacksonized
public class EmailInfoDto {
    private String id;
    private List<String> to;
    private String subject;
    private String body;
    private EmailStatus status;
    private String errorMessage;
}
