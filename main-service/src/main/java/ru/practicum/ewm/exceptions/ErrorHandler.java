package ru.practicum.ewm.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

/**
 * Обработчик исключений
 */
@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    /**
     * Not found
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFound(final NotFoundException e) {
        log.error("404 {}", e.getMessage(), e);

        return ApiError.builder()
                .errors(createError(e))
                .status("NOT_FOUND")
                .reason("The required object was not found.")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    //DataIntegrityViolationException
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError dataConflict(final DataIntegrityViolationException e) {
        log.error("409 {}", e.getMessage(), e);

        return ApiError.builder()
                .errors(createError(e))
                .status("CONFLICT")
                .reason("Integrity constraint has been violated")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    //IllegalArgumentException
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError dataConflict(final IllegalArgumentException e) {
        log.error("400 {}", e.getMessage(), e);

        return ApiError.builder()
                .errors(createError(e))
                .status("BAD_REQUEST")
                .reason("Wrong arguments in request")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    //ConditionsAreNotMetException
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError conditionsAreNotMetException(final ConditionsAreNotMetException e) {
        log.error("403 {}", e.getMessage(), e);

        return ApiError.builder()
                .errors(createError(e))
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    //AccessDeniedException
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError accessDenied(final AccessDeniedException e) {
        log.error("403 {}", e.getMessage(), e);

        return ApiError.builder()
                .errors(createError(e))
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    private String createError(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }
}