package ru.practicum.ewm.request.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Данные для изменения информации о событии
 */
@Getter
@Setter
@ToString
public class UpdateEventRequest {
    @Size(min = 20, message = "{validation.size.too_short}")
    @Size(max = 2000, message = "{validation.size.too_long}")
    private String annotation;
    private Long category;
    @Size(min = 20, message = "{validation.size.too_short}")
    @Size(max = 7000, message = "{validation.size.too_long}")
    private String description;
    //Новые дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    private String eventDate;
    @NotNull
    private Long eventId;
    private Boolean paid;
    private Integer participantLimit;
    @Size(min = 3, message = "{validation.size.too_short}")
    @Size(max = 120, message = "{validation.size.too_long}")
    private String title;
}
