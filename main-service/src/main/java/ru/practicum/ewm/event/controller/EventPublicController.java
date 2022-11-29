package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.ClientService;
import ru.practicum.ewm.event.model.SortType;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.service.EventPublicService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <h2>Публичный API событий</h2>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
@Validated
public class EventPublicController {

    private final EventPublicService service;
    private final ClientService statistics;

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
    public List<EventFullOutDto> getAll(@RequestParam(name = "text", required = false) @NotEmpty String text,
                                        @RequestParam(name = "categories", required = false) Integer[] categories,
                                        @RequestParam(name = "paid", required = false) Boolean paid,
                                        @RequestParam(name = "rangeStart", required = false)
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false)
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                        @RequestParam(name = "onlyAvailable", defaultValue = "false")
                                        Boolean onlyAvailable,
                                        @RequestParam(name = "sort", required = false) SortType sort,
                                        @RequestParam(name = "from", defaultValue = "0")
                                        @PositiveOrZero Integer from,
                                        @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size,
                                        HttpServletRequest request) {
        log.info("Получение событий с возможностью фильтрации {}", text);
        statistics.hit(request.getRequestURI(), request.getRemoteAddr());

        return service.getAll(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    /**
     * <h3>Получение подробной информации об опубликованном событии по его идентификатору</h3>
     * <li>событие должно быть опубликовано</li>
     * <li>информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов</li>
     * <li>информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики</li>
     */
    @GetMapping("/{id}")
    public EventFullOutDto getById(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        log.info("Получение события по id = {}", id);
        statistics.hit(request.getRequestURI(), request.getRemoteAddr());

        return service.getById(id);
    }
}
