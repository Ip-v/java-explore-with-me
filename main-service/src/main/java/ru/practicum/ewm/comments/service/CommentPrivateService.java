package ru.practicum.ewm.comments.service;

import ru.practicum.ewm.comments.model.dto.CommentDto;
import ru.practicum.ewm.comments.model.dto.NewCommentDto;
import ru.practicum.ewm.utils.State;

import java.util.List;

/**
 * <h2>Сервис приватного API комментариев</h2>
 */
public interface CommentPrivateService {
    /**
     * <h3>Добавление пользователем нового комментария к событию.</h3>
     * Текст комментария не должен быть пустым.<br>
     * id пользователя обязательно.<br>
     * id события обязательно.<br>
     * Пользователь должен быть подтвержденным участником или хозяином события<br>
     * Событие должно быть опубликовано
     */
    CommentDto add(Long userId, Long eventId, NewCommentDto dto, Boolean anonymous);

    /**
     * Получение списка пользовательских комментариев с возможностью фильтрации по статусу
     */
    List<CommentDto> getAllByUserId(Long userId, int from, int size, State state);


    /**
     * Удаление своего комментария пользователем
     */
    void delete(Long userId, Long commentId);

    /**
     * <h3>Обновление пользовательского комментария</h3>
     * Обновление возможно только для неопубликованных комментариев.<br>
     * При обновлении опубликованного комментария, оно переходит в статус неопубликованного.
     */
    CommentDto update(Long userId, Long commentId, NewCommentDto dto);
}
