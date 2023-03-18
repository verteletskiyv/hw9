package ua.profitsoft.hw9.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.profitsoft.hw9.dto.RestResponse;
import ua.profitsoft.hw9.exception.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        bindingResult.getFieldErrors().forEach(e -> errorMessage
                .append(e.getField())
                .append(" - ")
                .append(e.getDefaultMessage() == null ? e.getCode() : e.getDefaultMessage())
                .append("; "));
        throw new ValidationException(errorMessage.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private RestResponse handleException(ValidationException e) {
        return new RestResponse(e.getMessage());
    }
}
