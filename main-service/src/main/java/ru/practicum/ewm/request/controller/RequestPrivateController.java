package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.EventRequestService;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Закрытый API для работы с запросами текущего пользователя на участие в событиях
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class RequestPrivateController {
    private final EventRequestService service;

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     */
    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable @Positive(message = "The number must be > then 0")
                                                     Long userId) {
        log.info("Получение информации о заявках пользователя {} на участие в чужих событиях", userId);
        return service.getRequests(userId);
    }

    /**
     * <h3>Добавление запроса от текущего пользователя на участие в событии</h3>
     * <li>нельзя добавить повторный запрос</li>
     * <li>инициатор события не может добавить запрос на участие в своём событии</li>
     * <li>нельзя участвовать в неопубликованном событии</li>
     * <li>если у события достигнут лимит запросов на участие - необходимо вернуть ошибку</li>
     * <li>если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного</li>
     */
    @PostMapping
    public ParticipationRequestDto addRequest(@PathVariable @Positive(message = "The number must be > then 0")
                                              Long userId, @RequestParam @Positive Long eventId) {
        log.info("Добавление запроса от пользователя {} на участие в событии {}", userId, eventId);
        return service.addRequest(userId, eventId);
    }

    /**
     * Отмена своего запроса на участие в событии
     */
    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable @Positive(message = "The number must be > then 0")
                                                 Long userId,
                                                 @PathVariable @Positive(message = "The number must be > then 0")
                                                 Long requestId) {
        log.info("Отмена своего запроса {} пользователем {} на участие в событии", requestId, userId);

        return service.cancelRequest(requestId, userId);
    }
}
