package ru.practicum.ewm.event.model.dto.validation;

import ru.practicum.ewm.event.model.dto.EventFullDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ru.practicum.ewm.utils.DateFormat.DATE_FORMATTER;

/**
 * Валидатор корректности даты события
 */
public class EventDateDelayValidator implements ConstraintValidator<EventDateDelay, EventFullDto> {
    @Override
    public boolean isValid(final EventFullDto e, final ConstraintValidatorContext context) {
        LocalDateTime eventDate = LocalDateTime.parse(e.getEventDate(), DATE_FORMATTER);
        return ChronoUnit.HOURS.between(LocalDateTime.now(), eventDate) >= 2;
    }
}
