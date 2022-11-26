package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.model.EventMapper;
import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.event.repository.LocationRepository;
import ru.practicum.ewm.exceptions.AccessDeniedException;
import ru.practicum.ewm.exceptions.ConditionsAreNotMetException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.utils.DateFormat.DATE_FORMATTER;

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

    @Override
    public List<EventShortDto> getEvents(Long userId, Integer from, Integer size) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));
        Pageable pageRequest = PageRequest.of(from / size, size);
        List<Event> events = repository.findAllByInitiatorId(userId, pageRequest);
        return events.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullOutDto changeEvent(Long userId, EventFullDto dto) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));
        
        Event update = dto.getId() == null ? findEventByInitiatorId(userId) : repository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", dto.getId())));

        if (!userId.equals(update.getInitiator().getId())) {
            throw new RuntimeException("Access denied"); //todo add exception
        }
        if (update.getState() == EventState.PUBLISHED) {
            throw new ConditionsAreNotMetException("ADD exception"); //todo add exception
        }
        saveChangesToEvent(dto, update);
        update.setModerationRequired(true);
        update.setState(EventState.PENDING);

        Event save = repository.save(update);
        log.info("Event id = {} successfully changed", save.getId());
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

    private Event findEventByInitiatorId(Long userId) {
        List<Event> events = repository.findAllByInitiatorId(userId);
        if (events == null || events.size() == 0) {
            throw new NotFoundException(String.format("No events found for user wtih id=%s.", userId));
        }
        if (events.size() > 1) {
            throw new ConditionsAreNotMetException("More than 1 events found.");
        }
        Event event = events.get(0);
        if (event.getState() == EventState.PUBLISHED) {
            throw new ConditionsAreNotMetException(String.format("Event cannot be updated. Wrong state %s.",
                    event.getState()));
        }
        return event;
    }

    @Override
    @Transactional
    public EventFullOutDto addEvent(Long userId, EventFullDto dto) {
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
                .state(EventState.PENDING)
                .title(dto.getTitle())
                .build();
        Event save = repository.save(event);

        return EventMapper.toEventFullOutDto(save);
    }

    @Override
    public EventFullOutDto getEventById(Long userId, Long eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));

        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", eventId)));

        if (!userId.equals(event.getInitiator().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        return EventMapper.toEventFullOutDto(event);
    }

    @Override
    @Transactional
    public EventFullOutDto cancelEventById(Long userId, Long eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));

        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", eventId)));

        if (!userId.equals(event.getInitiator().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        if (!event.getState().equals(EventState.PENDING)) {
            throw new ConditionsAreNotMetException("Only PENDING events can be canceled.");
        }

        event.setState(EventState.CANCELED);
        Event save = repository.save(event);
        log.info("Event with id={} successfully canceled", eventId);

        return EventMapper.toEventFullOutDto(save);
    }

    @Override
    public EventFullDto getRequests(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {//todo implement
        return null;
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {//todo implement
        return null;
    }
}
