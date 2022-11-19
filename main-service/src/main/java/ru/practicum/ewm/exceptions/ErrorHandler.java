package ru.practicum.ewm.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public ApiError notFound(final Exception e) {
        //todo implement
        log.warn(e.getMessage());
        return new ApiError();
    }
}