package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.model.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Admin event API service
 */
public interface EventAdminService {

    /**
     * Поиск событий<br>
     * <i>Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия</i>
     */
    List<EventFullOutDto> getEvents(Integer[] users, String[] states, Integer[] categories, LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd, Integer from, Integer size);

    /**
     * Редактирование события<br>
     * <i>Редактирование данных любого события администратором. Валидация данных не требуется.</i>
     */
    EventFullOutDto updateEvent(EventFullDto dto, Long eventId);

    /**
     * <h3>Публикация события</h3>
     * <li>дата начала события должна быть не ранее чем за час от даты публикации.</li>
     * <li>событие должно быть в состоянии ожидания публикации</li>
     */
    EventFullOutDto publishEvent(Long eventId);

    /**
     * Отклонение события<br>
     * <i>Событие не должно быть опубликовано.</i>
     */
    EventFullOutDto rejectEvent(Long eventId);
}
