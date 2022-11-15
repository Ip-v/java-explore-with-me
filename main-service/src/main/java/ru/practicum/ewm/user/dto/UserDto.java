package ru.practicum.ewm.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Пользователь
 */
public class UserDto {
    @NotNull
    @Email
    private String email;
    //readonly = true
    private Long id;
    @NotEmpty
    private String name;
}
