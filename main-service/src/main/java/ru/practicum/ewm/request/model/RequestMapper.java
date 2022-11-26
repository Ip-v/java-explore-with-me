package ru.practicum.ewm.request.model;

import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import static ru.practicum.ewm.utils.DateFormat.DATE_FORMATTER;

public class RequestMapper {
    public static ParticipationRequestDto toParticipantRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .created(DATE_FORMATTER.format(request.getCreatedOn()))
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getUser().getId())
                .status(request.getConfirmed().name())
                .build();
    }
}
