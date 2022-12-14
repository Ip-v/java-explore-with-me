package ru.practicum.ewm.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class CompilationDto {
    private List<EventShortDto> events;
    @NotNull(groups = Create.class)
    private Long id;
    @NotNull(groups = Create.class)
    private Boolean pinned;
    @NotEmpty(groups = Create.class)
    private String title;
}
