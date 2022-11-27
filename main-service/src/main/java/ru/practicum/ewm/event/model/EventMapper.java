package ru.practicum.ewm.event.model;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.user.dto.UserMapper;

import java.util.Locale;

import static ru.practicum.ewm.utils.DateFormat.DATE_FORMATTER;

//TODO comments
@Slf4j
public class EventMapper {
    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(event.getCategory().getId())
                //.confirmedRequests(event.getConfirmedRequest)
                .confirmedRequests(0L) //todo add to event field
                .createdOn(event.getCreatedOn().toString())
                .description(event.getDescription())
                .eventDate(DATE_FORMATTER.format(event.getEventDate()))
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                //.publishedOn(event.getPublishedOn().toString())
                .requestModeration(event.getModerationRequired())
                .state(event.getState().name())
                .title(event.getTitle())
                //.views(event.getViews)
                .build();
    }

    public static EventFullOutDto toEventFullOutDto(Event event, Long views) {
        return EventFullOutDto.builder()
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                //.confirmedRequests(0L) //todo update
                .createdOn(DATE_FORMATTER.format(event.getCreatedOn()))
                .description(event.getDescription())
                .eventDate(DATE_FORMATTER.format(event.getEventDate()))
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                //.publishedOn(DATE_FORMATTER.format(event.getPublishedOn()))
                .requestModeration(event.getModerationRequired())
                .state(event.getState().name())
                .title(event.getTitle())
                //.views(0L) //todo update
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(event.getCategory().getId())
                .confirmedRequests(0L) ////todo add to event field
                .eventDate(DATE_FORMATTER.format(event.getEventDate()))
                .id(event.getId())
                .initiator(event.getInitiator().getId())
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(0L) //TODO implement
                .build();
    }

    public static EventState toEventState(String stateName) {
        return EventState.valueOf(stateName.toUpperCase(Locale.ROOT));
    }
}
