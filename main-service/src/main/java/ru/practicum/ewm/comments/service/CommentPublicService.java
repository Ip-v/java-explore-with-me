package ru.practicum.ewm.comments.service;

import ru.practicum.ewm.comments.model.dto.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentPublicService {
    /**
     * Получение опубликованных комментариев к событию с возможностью фильтрации
     */
    List<CommentDto> getAllByEventId(Long eventId, String text, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                     int from, int size);

    /**
     * Получение опубликованного комментария по его id
     */
    CommentDto getById(Long id);
}
