package ru.practicum.ewm.event.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Новое событие
 */
@Getter
@Setter
@ToString
public class NewEventDto {
    @Size(min = 20, message = "{validation.size.too_short}")
    @Size(max = 2000, message = "{validation.size.too_long}")
    private String annotation;
    @NotNull(groups = Create.class)
    private Long category;
    @Size(min = 20, message = "{validation.size.too_short}")
    @Size(max = 7000, message = "{validation.size.too_long}")
    private String description;
    //Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    @NotNull(groups = Create.class)
    private String eventDate;
    private Location location;
    //default: false
    private Boolean paid = false;
    private Integer participantLimit = 0;
    private Boolean requestModeration = true;
    @Size(min = 3, message = "{validation.size.too_short}")
    @Size(max = 120, message = "{validation.size.too_long}")
    private String title;

}
