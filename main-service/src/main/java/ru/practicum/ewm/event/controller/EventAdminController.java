package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

/**
 * API админитсратора для работы с событиями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class EventAdminController {

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
                                         @RequestParam(name = "from", defaultValue = "10") Integer from,
                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Поиск событий по условиям");
        //todo implement
        return null;
    }

    /**
     * Редактирование события<br>
     * <i>Редактирование данных любого события администратором. Валидация данных не требуется.</i>
     */
    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@RequestBody EventFullDto dto,
                                    @PathVariable @Positive(message = "The number must be greater then 0") Long eventId) {
        log.info("Редактирование события {} -> {}", eventId, dto);
        //todo implement
        return null;
    }

    /**
     * <h3>Публикация события</h3>
     * <li>дата начала события должна быть не ранее чем за час от даты публикации.</li>
     * <li>событие должно быть в состоянии ожидания публикации</li>
     */
    @PatchMapping("/{eventId}/publish")
    public EventShortDto publishEvent(@PathVariable @Positive(message = "The number must be greater then 0")
                                      Long eventId) {
        log.info("Публикация события {}", eventId);
        //todo implement
        return null;
    }

    /**
     * Отклонение события<br>
     * <i>Обратите внимание: событие не должно быть опубликовано.</i>
     *
     * @param eventId
     * @return
     */
    @PatchMapping("/{eventId}/reject")
    public EventShortDto rejectEvent(@PathVariable @Positive(message = "The number must be greater then 0")
                                     Long eventId) {
        log.info("Отклонение события {}", eventId);
        //todo implement
        return null;
    }
}
