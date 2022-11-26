package ru.practicum.ewm.exceptions;

/**
 * Доступ запрещен
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
