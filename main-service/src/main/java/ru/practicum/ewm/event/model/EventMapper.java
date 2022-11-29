package ru.practicum.ewm.event.model;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.utils.State;

import java.util.Locale;

import static ru.practicum.ewm.utils.DateFormat.DATE_FORMATTER;

/**
 * Event Mapper
 */
@Slf4j
public class EventMapper {
    public static EventFullOutDto toEventFullOutDto(Event event, Long views) {
        return EventFullOutDto.builder()
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .confirmedRequests(event.getRequests() == null ? 0 : event.getRequests().size())
                .createdOn(DATE_FORMATTER.format(event.getCreatedOn()))
                .description(event.getDescription())
                .eventDate(DATE_FORMATTER.format(event.getEventDate()))
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn() == null ? null : DATE_FORMATTER.format(event.getPublishedOn()))
                .requestModeration(event.getModerationRequired())
                .state(event.getState().name())
                .title(event.getTitle())
                .views(views)
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(event.getCategory().getId())
                .confirmedRequests(event.getRequests() == null ? 0 : event.getRequests().size())
                .eventDate(DATE_FORMATTER.format(event.getEventDate()))
                .id(event.getId())
                .initiator(event.getInitiator().getId())
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }

    public static State toEventState(String stateName) {
        return State.valueOf(stateName.toUpperCase(Locale.ROOT));
    }
}
