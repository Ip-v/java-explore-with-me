package ru.practicum.ewm.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Пользователь (краткая информация)
 */
@Getter
@Setter
@ToString
@Builder
public class UserShortDto {
    @NotNull
    private Long id;
    @NotEmpty
    private String name;
}
