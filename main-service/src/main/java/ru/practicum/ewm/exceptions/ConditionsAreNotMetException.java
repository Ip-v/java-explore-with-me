package ru.practicum.ewm.exceptions;

/**
 * Не выполнены условия для совершения операции
 */
public class ConditionsAreNotMetException extends RuntimeException {
    public ConditionsAreNotMetException(String s) {
        super(s);
    }
}
