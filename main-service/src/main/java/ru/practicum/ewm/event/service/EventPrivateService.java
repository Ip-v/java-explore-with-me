package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.EventFullOutDto;
import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Private API service
 */
public interface EventPrivateService {
    /**
     * Получение событий добавленных текущим пользователем
     */
    List<EventShortDto> getEvents(Long userId, Integer from, Integer size);

    /**
     * <h3>Изменение события добавленного текущим пользователем</h3>
     * <li>изменить можно только отмененные события или события в состоянии ожидания модерации</li>
     * <li>если редактируется отменённое событие, то оно автоматически переходит в состояние ожидания модерации</li>
     * <li>дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента</li>
     */
    EventFullOutDto changeEvent(Long userId, EventFullDto dto);

    /**
     * <h3>Добавление нового события</h3>
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента.
     */
    EventFullOutDto addEvent(Long userId, EventFullDto dto);

    /**
     * Получение полной информации о событии добавленном текущим пользователем
     */
    EventFullOutDto getEventById(Long userId, Long eventId);

    /**
     * Отмена события добавленного текущим пользователем.<br>
     *
     * <i>Отменить можно только событие в состоянии ожидания модерации.</i>
     */
    EventFullOutDto cancelEventById(Long userId, Long eventId);

    /**
     * Получение информации о запросах на участие в событии текущего пользователя
     */
    List<ParticipationRequestDto> getRequests(Long userId, Long eventId);

    /**
     * <h3>Подтверждение чужой заявки на участие в событии текущего пользователя</h3>
     * <li>если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется</li>
     * <li>нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие</li>
     * <li>если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить</li>
     */
    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    /**
     * Отклонение чужой заявки на участие в событии текущего пользователя
     */
    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId);
}
