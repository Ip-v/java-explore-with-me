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
import javax.validation.constraints.Size;

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
    @Size(min = 20, message = "{validation.size.too_short}")
    @Size(max = 2000, message = "{validation.size.too_long}")
    private String annotation;
    @NotNull(groups = Create.class)
    private Long category;
    private Long confirmedRequests;
    private String createdOn;
    @Size(min = 20, message = "{validation.size.too_short}")
    @Size(max = 7000, message = "{validation.size.too_long}")
    private String description;
    @NotNull(groups = Create.class)
    private String eventDate;
    private Long id;
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
    @Size(min = 3, message = "{validation.size.too_short}")
    @Size(max = 120, message = "{validation.size.too_long}")
    private String title;
    private Long views;
}
