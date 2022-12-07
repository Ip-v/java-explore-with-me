package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.client.ClientService;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventMapper;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.event.repository.LocationRepository;
import ru.practicum.ewm.exceptions.AccessDeniedException;
import ru.practicum.ewm.exceptions.ConditionsAreNotMetException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestMapper;
import ru.practicum.ewm.request.repository.EventRequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.utils.DateFormat.DATE_FORMATTER;
import static ru.practicum.ewm.utils.State.*;

/**
 * Event Service for private API
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventPrivateServiceImpl implements EventPrivateService {
    private final EventRepository repository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final EventRequestRepository eventRequestRepository;
    private final ClientService statistics;

    @Override
    public List<EventShortDto> getAll(Long userId, Integer from, Integer size) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));
        Pageable pageRequest = PageRequest.of(from / size, size);
        List<Event> events = repository.findAllByInitiatorId(userId, pageRequest);

        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullOutDto change(Long userId, EventFullDto dto) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));

        Event update = dto.getId() == null ? findEventByInitiatorId(userId) : repository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", dto.getId())));

        if (!userId.equals(update.getInitiator().getId())) {
            throw new AccessDeniedException("Access denied");
        }
        if (update.getState() == PUBLISHED) {
            throw new ConditionsAreNotMetException("ADD exception");
        }
        saveChangesToEvent(dto, update);
        update.setModerationRequired(true);
        update.setState(PENDING);

        Event save = repository.save(update);
        log.info("Event id = {} successfully changed", save.getId());

        return EventMapper.toEventFullOutDto(save, getViews(save.getId()));
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

    private Event findEventByInitiatorId(Long userId) {
        List<Event> events = repository.findAllByInitiatorId(userId);
        if (events == null || events.size() == 0) {
            throw new NotFoundException(String.format("No events found for user wtih id=%s.", userId));
        }
        if (events.size() > 1) {
            throw new ConditionsAreNotMetException("More than 1 events found.");
        }
        Event event = events.get(0);
        if (event.getState() == PUBLISHED) {
            throw new ConditionsAreNotMetException(String.format("Event cannot be updated. Wrong state %s.",
                    event.getState()));
        }

        return event;
    }

    @Override
    @Transactional
    public EventFullOutDto add(Long userId, EventFullDto dto) {
        final User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));
        final Category category = categoryRepository.findById(dto.getCategory()).orElseThrow(() ->
                new NotFoundException(String.format("Category with id=%s not found.", dto.getId())));
        final Location location = locationRepository.save(dto.getLocation());

        Event event = Event.builder()
                .annotation(dto.getAnnotation())
                .category(category)
                .description(dto.getDescription())
                .eventDate(LocalDateTime.parse(dto.getEventDate(), DATE_FORMATTER))
                .location(location)
                .initiator(user)
                .paid(dto.getPaid())
                .participantLimit(dto.getParticipantLimit())
                .createdOn(LocalDateTime.now())
                .moderationRequired(dto.getRequestModeration())
                .state(PENDING)
                .title(dto.getTitle())
                .build();
        Event save = repository.save(event);

        return EventMapper.toEventFullOutDto(save, getViews(save.getId()));
    }

    @Override
    public EventFullOutDto getById(Long userId, Long eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));

        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", eventId)));

        if (!userId.equals(event.getInitiator().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        return EventMapper.toEventFullOutDto(event, getViews(eventId));
    }

    @Override
    @Transactional
    public EventFullOutDto cancel(Long userId, Long eventId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id=%s not found", userId));
        }

        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", eventId)));

        if (!userId.equals(event.getInitiator().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        if (!event.getState().equals(PENDING)) {
            throw new ConditionsAreNotMetException("Only PENDING events can be canceled.");
        }

        event.setState(CANCELED);
        Event save = repository.save(event);

        log.info("Event with id={} successfully canceled", eventId);

        return EventMapper.toEventFullOutDto(save, getViews(save.getId()));
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId, Long eventId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id=%s not found", userId));
        }

        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", eventId)));

        if (!userId.equals(event.getInitiator().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        log.debug("Request from db for all user {} event {} requests", userId, eventId);

        return eventRequestRepository.findByEvent(event)
                .stream()
                .map(RequestMapper::toParticipantRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirm(Long userId, Long eventId, Long reqId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id=%s not found", userId));
        }

        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", eventId)));

        if (!userId.equals(event.getInitiator().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        Request request = eventRequestRepository.findById(reqId).orElseThrow(() ->
                new NotFoundException(String.format("Requset with id=%s not found", reqId)));

        if (!event.getModerationRequired() || event.getParticipantLimit() == 0) {
            return RequestMapper.toParticipantRequestDto(request);
        }

        long confirmedRequests = eventRequestRepository.countByEventAndConfirmed(event, CONFIRMED);
        if (confirmedRequests == event.getParticipantLimit()) {
            throw new ConditionsAreNotMetException("Reached participant limit");
        }

        request.setConfirmed(CONFIRMED);
        Request save = eventRequestRepository.save(request);
        log.info("Request {} for event {} successfully confirmed by user {}", reqId, eventId, userId);

        if (confirmedRequests + 1 == event.getParticipantLimit()) {
            rejectAllPendingRequests(event);
        }

        return RequestMapper.toParticipantRequestDto(save);
    }

    private void rejectAllPendingRequests(Event event) {
        log.info("Participant limit reached for event id = {}. Rehecting all pending requests.", event.getId());
        eventRequestRepository.rejectAllPendingRequests(event.getId());
    }

    @Override
    @Transactional
    public ParticipationRequestDto reject(Long userId, Long eventId, Long reqId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id=%s not found", userId));
        }

        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", eventId)));

        if (!userId.equals(event.getInitiator().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        Request request = eventRequestRepository.findById(reqId).orElseThrow(() ->
                new NotFoundException(String.format("Requset with id=%s not found", reqId)));

        request.setConfirmed(REJECTED);
        Request save = eventRequestRepository.save(request);
        log.info("Request with id={} successfully rejected", reqId);

        return RequestMapper.toParticipantRequestDto(save);
    }

    private Long getViews(long id) {
        return statistics.getStats("/events/" + id);
    }
}
