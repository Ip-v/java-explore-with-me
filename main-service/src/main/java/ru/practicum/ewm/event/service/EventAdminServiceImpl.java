package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventMapper;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.ConditionsAreNotMetException;
import ru.practicum.ewm.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventAdminServiceImpl implements EventAdminService{
    private final EventRepository eventRepository;

    @Override
    public List<EventShortDto> getEvents(Integer[] users, String[] states, Integer[] categories,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        //TODO implement
        return null;
    }

    @Override
    public EventFullDto updateEvent(EventFullDto dto, Long eventId) {
        //TODO implement
        return null;
    }

    @Override
    @Transactional
    public EventFullOutDto publishEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", eventId)));

        if (!event.getState().equals(EventState.PENDING)) {
            throw new ConditionsAreNotMetException("Event should be in PENDING state");
        }
        event.setPublishedOn(LocalDateTime.now());
        event.setState(EventState.PUBLISHED);

        Event save = eventRepository.save(event);
        log.info("Event id={} successfully published", eventId);
        return EventMapper.toEventFullOutDto(save);
    }

    @Override
    public EventShortDto rejectEvent(Long eventId) {
        //TODO implement
        return null;
    }
}
