package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Private Event request API
 */
public interface EventRequestService {

    /**
     * <h3>Добавление запроса от текущего пользователя на участие в событии</h3>
     * <li>нельзя добавить повторный запрос</li>
     * <li>инициатор события не может добавить запрос на участие в своём событии</li>
     * <li>нельзя участвовать в неопубликованном событии</li>
     * <li>если у события достигнут лимит запросов на участие - необходимо вернуть ошибку</li>
     * <li>если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного</li>
     */
    ParticipationRequestDto addRequest(Long userId, Long eventId);

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     */
    List<ParticipationRequestDto> getRequests(Long userId);

    /**
     * Отмена своего запроса на участие в событии
     */
    ParticipationRequestDto cancelRequest(Long requestId, Long userId);
}
