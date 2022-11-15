package ru.practicum.ewm.category.dto;

import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Данные для добавления новой категории
 */
public class NewCategoryDto {
    @NotEmpty(groups = Create.class)
    private String name;
}
