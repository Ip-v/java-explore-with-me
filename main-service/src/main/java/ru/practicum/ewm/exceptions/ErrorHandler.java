package ru.practicum.ewm.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Обработчик исключений
 */
@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    /**
     * Не найдено
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFound(final NotFoundException e) {
        log.error(e.getMessage());
        return ApiError.builder()
                .status("NOT_FOUND")
                .reason("The required object was not found.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    //DataIntegrityViolationException
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError dataConflict(final DataIntegrityViolationException e) {
        log.error(e.getMessage());
        return ApiError.builder()
                .status("CONFLICT")
                .reason("Integrity constraint has been violated")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
    //IllegalArgumentException
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError dataConflict(final IllegalArgumentException e) {
        log.error(e.getMessage());
        return ApiError.builder()
                .status("BAD_REQUEST")
                .reason("Wrong arguments in request")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}