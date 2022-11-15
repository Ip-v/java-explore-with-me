package ru.practicum.ewm.request.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.event.model.Location;

/**
 * Информация для редактирования события администратором. Все поля необязательные. Значение полей не валидируется.
 */
@Getter
@Setter
@ToString
public class AdminUpdateEventRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
}
