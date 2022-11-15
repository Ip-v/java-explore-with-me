package ru.practicum.ewm.compilation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Подборка событий
 */
@Getter
@Setter
@ToString
public class NewCompilationDto {
    private List<Integer> events;
    private Boolean pinned;
    @NotNull(groups = Create.class)
    private String title;
}
