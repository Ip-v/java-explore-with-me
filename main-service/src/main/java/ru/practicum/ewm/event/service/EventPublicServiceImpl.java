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
import ru.practicum.ewm.event.model.SortType;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.ConditionsAreNotMetException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.EventRequestRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventPublicServiceImpl implements EventPublicService {
    private final EventRepository repository;
    private final EventRequestRepository eventRequestRepository;

    @Override
    public List<EventShortDto> getEvents(String text, Integer[] categories, Boolean paid, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, Boolean onlyAvailable, SortType sort, Integer from,
                                         Integer size) {
        //todo implement
        return null;
    }

    @Override
    public EventFullOutDto getEventById(Long id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", id)));

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConditionsAreNotMetException("Event isn't published.");
        }
        //todo get views from statistics
        //todo save to statistics
        EventFullOutDto out = EventMapper.toEventFullOutDto(event);
        out.setConfirmedRequests(eventRequestRepository.countByEventAndConfirmed(event, RequestStatus.CONFIRMED));
        return out;
    }
}
