package ru.practicum.ewm.exceptions;

/**
 * Not found event exception
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String s) {
        super(s);
    }
}
