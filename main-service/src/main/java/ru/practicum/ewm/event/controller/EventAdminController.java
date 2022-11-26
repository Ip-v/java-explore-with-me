package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.event.service.EventAdminService;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

/**
 * API админитсратора для работы с событиями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/events")
public class EventAdminController {

    private final EventAdminService service;

    /**
     * Поиск событий<br>
     * <i>Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия</i>
     */
    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "users", required = false) Integer[] users,
                                         @RequestParam(name = "states", required = false) String[] states,
                                         @RequestParam(name = "categories", required = false) Integer[] categories,
                                         @RequestParam(name = "rangeStart", required = false) LocalDateTime rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false) LocalDateTime rangeEnd,
                                         @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Поиск событий по users {} states {} categories {} rangeStart {} rangeEnd {} from {} size {}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        return service.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /**
     * Редактирование события<br>
     * <i>Редактирование данных любого события администратором. Валидация данных не требуется.</i>
     */
    @PutMapping("/{eventId}")
    public EventFullOutDto updateEvent(@RequestBody EventFullDto dto,
                                    @PathVariable @Positive(message = "The number must be greater then 0") Long eventId) {
        log.info("Редактирование события {} -> {}", eventId, dto);
        return service.updateEvent(dto, eventId);
    }

    /**
     * <h3>Публикация события</h3>
     * <li>дата начала события должна быть не ранее чем за час от даты публикации.</li>
     * <li>событие должно быть в состоянии ожидания публикации</li>
     */
    @PatchMapping("/{eventId}/publish")
    public EventFullOutDto publishEvent(@PathVariable @Positive(message = "The number must be greater then 0")
                                      Long eventId) {
        log.info("Публикация события {}", eventId);
        //todo add 1 hour validation
        return service.publishEvent(eventId);
    }

    /**
     * Отклонение события<br>
     * <i>Событие не должно быть опубликовано.</i>
     */
    @PatchMapping("/{eventId}/reject")
    public EventFullOutDto rejectEvent(@PathVariable @Positive(message = "The number must be greater then 0")
                                     Long eventId) {
        log.info("Отклонение события {}", eventId);
        return service.rejectEvent(eventId);
    }
}
