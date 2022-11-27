package ru.practicum.ewm.event.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.ConditionsAreNotMetException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.request.model.QRequest;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.EventRequestRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventPublicServiceImpl implements EventPublicService {
    private final EventRepository repository;
    private final EventRequestRepository eventRequestRepository;

    @Override
    public List<EventFullOutDto> getEvents(String text, Integer[] categories, Boolean paid, LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd, Boolean onlyAvailable, SortType sort, Integer from,
                                           Integer size) {
        List<BooleanExpression> expression = new ArrayList<>();
        QEvent event = QEvent.event;

        expression.add(event.state.eq(EventState.PUBLISHED)
                .and(event.eventDate.after((rangeStart == null ? LocalDateTime.now() : rangeStart))));
        if (text != null) {
            expression.add(event.annotation.containsIgnoreCase(text)
                    .or(event.description.containsIgnoreCase(text)));
        }
        if (categories != null) {
            expression.add(event.category.id.in(categories));
        }
        if (paid != null) {
            expression.add(event.paid.eq(paid));
        }
        if (rangeEnd != null) {
            expression.add(event.eventDate.before(rangeEnd));
        }
        if (onlyAvailable) {
            QRequest request = QRequest.request;

            BooleanExpression noParticipantLimit = event.participantLimit.eq(0);
            expression.add(noParticipantLimit);

            BooleanExpression noModerationRequired = event.moderationRequired.eq(false)
                    .and(event.participantLimit.goe(request.count()));
            expression.add(noModerationRequired);

            BooleanExpression moderationRequired = event.moderationRequired.eq(true)
                    .and(event.participantLimit.goe(request.confirmed.eq(RequestStatus.CONFIRMED).count()));
            expression.add(moderationRequired);
        }

        BooleanExpression searchCriteria = expression.stream().reduce(BooleanExpression::and).get();

        if (sort != null && sort.equals(SortType.EVENT_DATE)) {
            Pageable pageable = PageRequest.of(from / size, size, Sort.by("eventDate").descending());
            return repository.findAll(searchCriteria, pageable)
                    .map(EventMapper::toEventFullOutDto)
                    .toList();
        }
        //todo add statistics
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("eventDate").descending());
        return repository.findAll(searchCriteria, pageable)
                .map(EventMapper::toEventFullOutDto)
                .toList();

    }

    @Override
    public EventFullOutDto getEventById(Long id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", id)));

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConditionsAreNotMetException("Event isn't published.");
        }
        //todo get views from statistics
        EventFullOutDto out = EventMapper.toEventFullOutDto(event);
        out.setConfirmedRequests(eventRequestRepository.countByEventAndConfirmed(event, RequestStatus.CONFIRMED));
        return out;
    }

    private Integer getViews(long id) {
        return clientService.getStats("/events/" + id);
    }
}
