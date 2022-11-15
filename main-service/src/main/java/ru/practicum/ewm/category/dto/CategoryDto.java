package ru.practicum.ewm.category.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Категория
 */
@Getter
@Setter
@ToString
public class CategoryDto {
    @NotNull(groups = Create.class)
    private Long id;
    @NotEmpty(groups = Create.class)
    private String name;
}
