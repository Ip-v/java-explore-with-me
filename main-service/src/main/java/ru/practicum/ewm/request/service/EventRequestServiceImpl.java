package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.ConditionsAreNotMetException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestMapper;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.EventRequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер запросов
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventRequestServiceImpl implements EventRequestService {
    private final EventRequestRepository repository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ParticipationRequestDto add(Long userId, Long eventId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));

        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id=%s not found", eventId)));

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConditionsAreNotMetException("User cannot add create request to owned event");
        }

        if (event.getState() != EventState.PUBLISHED) {
            throw new ConditionsAreNotMetException("Event state should be PUBLISHED");
        }
        long confirmedRequests = repository.countByEventAndConfirmed(event, RequestStatus.CONFIRMED);
        if (confirmedRequests >= event.getParticipantLimit()) {
            throw new ConditionsAreNotMetException("Reached participant limit");
        }

        Request request = Request.builder()
                .user(user)
                .event(event)
                .confirmed(RequestStatus.PENDING)
                .createdOn(LocalDateTime.now())
                .build();
        Request save = repository.save(request);

        log.info("User {} request for event {} successfully created", userId, eventId);
        return RequestMapper.toParticipantRequestDto(save);
    }

    @Override
    public List<ParticipationRequestDto> getAll(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));
        log.debug("Query all user {} request from db", userId);
        return repository.getAllUserRequests(userId)
                .stream()
                .map(RequestMapper::toParticipantRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(Long requestId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));
        Request request = repository.findById(requestId).orElseThrow(() ->
                new NotFoundException(String.format("Request with id=%s not found", requestId)));

        request.setConfirmed(RequestStatus.CANCELED);
        Request save = repository.save(request);
        log.info("Request id={} successfully CANCELED", requestId);
        return RequestMapper.toParticipantRequestDto(save);
    }
}
