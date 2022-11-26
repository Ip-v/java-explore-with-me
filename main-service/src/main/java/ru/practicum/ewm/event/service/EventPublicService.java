package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.model.SortType;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.model.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Public event API service
 */
public interface EventPublicService {
    /**
     * <h3>Получение событий с возможностью фильтрации</h3>
     * <p>
     * Это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
     * текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
     * если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут позже текущей даты и времени
     * информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
     * информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
     */
    List<EventFullOutDto> getEvents(String text, Integer[] categories, Boolean paid, LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd, Boolean onlyAvailable, SortType sort, Integer from,
                                  Integer size);

    /**
     * <h3>Получение подробной информации об опубликованном событии по его идентификатору</h3>
     * <li>событие должно быть опубликовано</li>
     * <li>информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов</li>
     * <li>информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики</li>
     */
    EventFullOutDto getEventById(Long id);
}
