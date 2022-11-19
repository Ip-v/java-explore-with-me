package ru.practicum.ewm.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Пользователь DTO
 */
@Getter
@Setter
@ToString
@Builder
public class UserDto {
    @NotNull
    @Email
    private String email;
    //readonly = true
    private Long id;
    @NotEmpty
    private String name;
}
