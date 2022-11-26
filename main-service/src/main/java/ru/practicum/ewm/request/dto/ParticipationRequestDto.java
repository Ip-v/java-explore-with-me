package ru.practicum.ewm.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.request.model.RequestStatus;

/**
 * Заявка на участие в событии
 */
@Getter
@Setter
@ToString
@Builder
public class ParticipationRequestDto {
    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private String status;
}
