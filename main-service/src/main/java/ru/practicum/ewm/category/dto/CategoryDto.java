package ru.practicum.ewm.category.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.utils.Create;
import ru.practicum.ewm.utils.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Категория
 */
@Getter
@Setter
@ToString
public class CategoryDto {
    @NotNull(groups = Update.class)
    private Long id;
    @NotEmpty(groups = Create.class)
    @NotEmpty(groups = Update.class)
    private String name;
}
