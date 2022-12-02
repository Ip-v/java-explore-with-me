package ru.practicum.ewm.event.model.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация проверки корректности даты события
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventDateDelayValidator.class)
public @interface EventDateDelay {
    String message() default "{EventDateDelay.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
