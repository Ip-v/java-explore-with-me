package ru.practicum.ewm.event.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.client.ClientService;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventMapper;
import ru.practicum.ewm.event.model.QEvent;
import ru.practicum.ewm.event.model.SortType;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.ConditionsAreNotMetException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.request.model.QRequest;
import ru.practicum.ewm.request.repository.EventRequestRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.utils.State.CONFIRMED;
import static ru.practicum.ewm.utils.State.PUBLISHED;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventPublicServiceImpl implements EventPublicService {
    private final EventRepository repository;
    private final EventRequestRepository eventRequestRepository;
    private final ClientService statistics;

    @Override
    public List<EventFullOutDto> getAll(String text, Integer[] categories, Boolean paid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, Boolean onlyAvailable, SortType sort, Integer from,
                                        Integer size) {
        List<BooleanExpression> expression = new ArrayList<>();
        QEvent event = QEvent.event;

        expression.add(event.state.eq(PUBLISHED)
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
                    .and(event.participantLimit.goe(request.confirmed.eq(CONFIRMED).count()));
            expression.add(moderationRequired);
        }

        BooleanExpression searchCriteria = expression.stream().reduce(BooleanExpression::and).get();

        if (sort != null && sort.equals(SortType.EVENT_DATE)) {
            Pageable pageable = PageRequest.of(from / size, size, Sort.by("eventDate").descending());
            return repository.findAll(searchCriteria, pageable)
                    .map(e -> EventMapper.toEventFullOutDto(e, getViews(e.getId())))
                    .toList();
        }
        Pageable pageable = PageRequest.of(from / size, size);
        log.info("Event list requst with filter");

        return repository.findAll(searchCriteria, pageable).stream()
                .map(e -> EventMapper.toEventFullOutDto(e, getViews(e.getId())))
                .sorted(Comparator.comparing(EventFullOutDto::getViews))
                .collect(Collectors.toList());

    }

    @Override
    public EventFullOutDto getById(Long id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", id)));

        if (!event.getState().equals(PUBLISHED)) {
            throw new ConditionsAreNotMetException("Event isn't published.");
        }
        EventFullOutDto out = EventMapper.toEventFullOutDto(event, getViews(event.getId()));
        out.setConfirmedRequests(eventRequestRepository.countByEventAndConfirmed(event, CONFIRMED));
        log.info("Event requst by id {}", id);

        return out;
    }

    private Long getViews(long id) {
        return statistics.getStats("/events/" + id);
    }
}
