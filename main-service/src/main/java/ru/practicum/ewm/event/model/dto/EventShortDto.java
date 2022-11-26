package ru.practicum.ewm.event.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotNull;

/**
 * Краткая информация о событии
 */
@Getter
@Setter
@ToString
@Builder
public class EventShortDto {
    @NotNull(groups = Create.class)
    private String annotation;
    @NotNull(groups = Create.class)
    private Long category;
    private Long confirmedRequests;
    //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @NotNull(groups = Create.class)
    private String eventDate;
    private Long id;
    @NotNull(groups = Create.class)
    private Long initiator;
    private Boolean paid;
    @NotNull(groups = Create.class)
    private String title;
    private Long views;
}
