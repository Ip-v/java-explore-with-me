package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
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

import static ru.practicum.ewm.utils.DateFormat.DATE_FORMATTER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventAdminServiceImpl implements EventAdminService{
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<EventShortDto> getEvents(Integer[] users, String[] states, Integer[] categories,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        //TODO implement
        return null;
    }

    @Override
    @Transactional
    public EventFullOutDto updateEvent(EventFullDto dto, Long eventId) {
        Event updated = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", eventId)));
        saveChangesToEvent(dto, updated);
        Event save = eventRepository.save(updated);
        log.info("Event id={} successfully updated", eventId);
        return EventMapper.toEventFullOutDto(save);
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
    @Transactional
    public EventFullOutDto rejectEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", eventId)));

        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConditionsAreNotMetException(
                    String.format("Event id=%s already published and couldn't be rejected", eventId));
        }
        event.setState(EventState.CANCELED);
        Event save = eventRepository.save(event);
        log.info("Event id={} successfully rejected", eventId);

        return EventMapper.toEventFullOutDto(save);
    }

    private void saveChangesToEvent(EventFullDto dto, Event update) {
        if (dto.getAnnotation() != null) {
            update.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            Category category = categoryRepository.findById(dto.getCategory()).orElseThrow(() ->
                    new NotFoundException(String.format("Category with id=%s not found.", dto.getId())));
            update.setCategory(category);
        }
        if (dto.getConfirmedRequests() != null) {
            //TODO add field
        }
        if (dto.getCreatedOn() != null) {
            update.setCreatedOn(LocalDateTime.parse(dto.getCreatedOn(), DATE_FORMATTER));
        }
        if (dto.getDescription() != null) {
            update.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            update.setEventDate(LocalDateTime.parse(dto.getEventDate(), DATE_FORMATTER));
        }
        if (dto.getLocation() != null) {
            update.setLocation(dto.getLocation());
        }
        if (dto.getPaid() != null) {
            update.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            update.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getPublishedOn() != null) {
            update.setPublishedOn(LocalDateTime.parse(dto.getPublishedOn(), DATE_FORMATTER));
        }
        if (dto.getTitle() != null) {
            update.setTitle(dto.getTitle());
        }
    }
}
