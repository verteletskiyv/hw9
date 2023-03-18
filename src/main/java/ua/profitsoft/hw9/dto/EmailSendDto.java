package ua.profitsoft.hw9.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import java.util.List;

@Getter
@Builder
@Jacksonized
public class EmailSendDto {
    @NotEmpty
    private List<@NotEmpty @Email String> to;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String body;
}
