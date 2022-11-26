package ru.practicum.ewm.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    public NotFoundException(String s) {
        super(s);
    }
}
