package ru.practicum.ewm.event.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.model.dto.validation.EventDateDelay;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.utils.Create;
import ru.practicum.ewm.utils.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Event full DTO
 */
@Getter
@Setter
@ToString
@Builder
@EventDateDelay(groups = {Create.class, Update.class})
public class EventFullDto {
    @NotEmpty(groups = Create.class)
    private String annotation;
    @NotNull(groups = Create.class)
    private Long category;
    private Long confirmedRequests;
    //Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private String createdOn;
    private String description;
    //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @NotNull(groups = Create.class)
    private String eventDate;
    private Long id;
    //@NotNull(groups = Create.class)
    private UserShortDto initiator;
    @NotNull(groups = Create.class)
    private Location location;
    @NotNull(groups = Create.class)
    private Boolean paid;
    private Integer participantLimit = 0;
    //Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    private String publishedOn;
    private Boolean requestModeration = true;
    private String state;
    @NotNull(groups = Create.class)
    private String title;
    private Long views;
}
