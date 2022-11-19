package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.SortType;
import ru.practicum.ewm.event.service.EventService;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <h2>Публичный API управления событиями</h2>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
@Validated
public class EventPublicController {

    private final EventService service;

    /**
     * <h3>Получение событий с возможностью фильтрации</h3>
     * <p>
     * Это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
     * текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
     * если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут позже текущей даты и времени
     * информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
     * информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
     */
    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "text") @NotEmpty String text,
                                         @RequestParam(name = "categories") Integer[] categories,
                                         @RequestParam(name = "paid") Boolean paid,
                                         @RequestParam(name = "rangeStart", required = false) LocalDateTime rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false) LocalDateTime rangeEnd,
                                         @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(name = "sort") SortType sort,
                                         @RequestParam(name = "from", defaultValue = "10") Integer from,
                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получение событий с возможностью фильтрации {}", text);
        //TODO  все параметры необязательные, поправить
        //TODO implement
//        BookingStatus status = BookingStatus.valueOf(state);
//        int page = from / size;
//        Pageable pageRequest = PageRequest.of(page, size, Sort.by("start").descending());
        return null;
    }

    /**
     * <h3>Получение подробной информации об опубликованном событии по его идентификатору</h3>
     * <li>событие должно быть опубликовано</li>
     * <li>информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов</li>
     * <li>информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики</li>
     *
     * @param id ид события
     * @return EventFullDto
     */
    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable(name = "id") Long id) {
        log.info("Получение событий с возможностью фильтрации {}", id);
        //TODO implement
        return null;
    }
}
