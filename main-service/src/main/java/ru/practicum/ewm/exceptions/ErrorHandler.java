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
        log.error("404 {}", e.getMessage());
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
        log.error("409 {}", e.getMessage());
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
        log.error("400 {}", e.getMessage());
        return ApiError.builder()
                .errors(createError(e))
                .status("BAD_REQUEST")
                .reason("Wrong arguments in request")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ApiError handleThrowable(final Exception e) {
//        log.error("500 {}", e.getMessage(), e);
//        return ApiError.builder()
//                .errors(createError(e))
//                .status("INTERNAL_SERVER_ERROR")
//                .reason("Internal server error")
//                .message(e.getLocalizedMessage())
//                .timestamp(LocalDateTime.now().toString())
//                .build();
//    }

    private String createError(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}