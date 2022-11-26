package ru.practicum.ewm.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotFoundException extends RuntimeException {
    private final ApiError apiError;

    public NotFoundException(String s) {
        super(s);
        apiError = ApiError.builder()
                .status("NOT_FOUND")
                .reason("The required object was not found.")
                .message(s)
                .timestamp(LocalDateTime.now().toString())
                .build();

    }
}
