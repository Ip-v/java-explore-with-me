package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Закрытй API для работы с событиями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class EventPrivateController {
    private final EventService service;

    /**
     * Получение событий добавленных текущим пользователем
     */
    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable @Positive(message = "The number must be greater then 0")
                                         Long userId,
                                         @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получение событий добавленных пользователем {} from {} size {}", userId, from, size);
        //todo implement
        return null;
    }

    /**
     * <h3>Изменение события добавленного текущим пользователем</h3>
     * <li>изменить можно только отмененные события или события в состоянии ожидания модерации</li>
     * <li>если редактируется отменённое событие, то оно автоматически переходит в состояние ожидания модерации</li>
     * <li>дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента</li>
     */
    @PatchMapping
    public EventFullDto changeEvent(@PathVariable @Positive(message = "The number must be greater then 0")
                                    Long userId, @RequestBody @Validated({Create.class}) EventFullDto dto) {
        log.info("Запрос изменения события добавленного пользователем {}, новые данные {}", userId, dto);
        //todo implement
        return null;
    }

    /**
     * <h3>Добавление нового события</h3>
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента.
     */
    @PostMapping
    public EventFullDto addEvent(@PathVariable @Positive(message = "The number must be greater then 0") Long userId,
                                 @RequestBody @Validated({Create.class}) EventFullDto dto) {
        log.info("Запрос добавления нового события пользователем {}, {}", userId, dto);
        //todo implement
        return null;
    }

    /**
     * Получение полной информации о событии добавленном текущим пользователем
     */
    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable @Positive(message = "The number must be > then 0") Long userId,
                                     @PathVariable @Positive(message = "The number must be > then 0") Long eventId) {
        log.info("Получение полной информации о событии {} добавленном текущим пользователем {}", userId, eventId);
        //todo implement
        return null;
    }

    /**
     * Отмена события добавленного текущим пользователем.<br>
     *
     * <i>Отменить можно только событие в состоянии ожидания модерации.</i>
     */
    @PatchMapping("/{eventId}")
    public EventFullDto cancelEventById(@PathVariable @Positive(message = "The number must be > then 0") Long userId,
                                        @PathVariable @Positive(message = "The number must be > then 0") Long eventId) {
        log.info("Отмена события {} добавленного текущим пользователем {}", eventId, userId);
        //todo implement
        return null;
    }

    /**
     * Получение информации о запросах на участие в событии текущего пользователя
     */
    @GetMapping("/{eventId}/requests")
    public EventFullDto getRequests(@PathVariable @Positive(message = "The number must be > then 0") Long userId,
                                    @PathVariable @Positive(message = "The number must be > then 0") Long eventId) {
        log.info("Получение информации о запросах на участие в событии {} пользователя {}", eventId, userId);
        //todo implement
        return null;
    }

    /**
     * <h3>Подтверждение чужой заявки на участие в событии текущего пользователя</h3>
     * <li>если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется</li>
     * <li>нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие</li>
     * <li>если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить</li>
     */
    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable @Positive(message = "The number must be > then 0")
                                                  Long userId,
                                                  @PathVariable @Positive(message = "The number must be > then 0")
                                                  Long eventId,
                                                  @PathVariable @Positive(message = "The number must be > then 0")
                                                  Long reqId) {
        log.info("Подтверждение заявки {} на участие в событии {} пользователем {}", reqId, eventId, userId);
        //todo implement
        return null;
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable @Positive(message = "The number must be > then 0")
                                                 Long userId,
                                                 @PathVariable @Positive(message = "The number must be > then 0")
                                                 Long eventId,
                                                 @PathVariable @Positive(message = "The number must be > then 0")
                                                 Long reqId) {
        log.info("Отклонение заявки {} на участие в событии {} пользователем {}", reqId, eventId, userId);
        //todo implement
        return null;
    }
}
