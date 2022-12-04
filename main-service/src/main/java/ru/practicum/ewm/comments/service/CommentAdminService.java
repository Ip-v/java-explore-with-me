package ru.practicum.ewm.comments.service;

import ru.practicum.ewm.comments.model.dto.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <h2>Сервис админа для API комментариев</h2>
 */
public interface CommentAdminService {

    /**
     * Публикация комментария
     */
    void publish(Long id);

    /**
     * Отклонение комментария
     */
    void reject(Long id);

    /**
     * Удаление комментария
     */
    void delete(Long id);

    /**
     * Обновление пользовательского комментария
     */
    CommentDto update(Long commentId, CommentDto dto);

    /**
     * Поиск комментариев с возможностью фильтрации
     */
    List<CommentDto> getAll(Long[] eventIds, String text, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from,
                            int size);
}
