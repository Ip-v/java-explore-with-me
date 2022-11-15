package ru.practicum.ewm.user.dto;

import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Данные нового пользователя
 */
public class NewUserRequest {
    @NotNull(groups = Create.class)
    @Email(groups = Create.class)
    private String email;
    @NotEmpty(groups = Create.class)
    private String name;
}
