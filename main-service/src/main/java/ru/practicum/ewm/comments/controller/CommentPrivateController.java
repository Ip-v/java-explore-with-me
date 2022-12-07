package ru.practicum.ewm.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.model.dto.CommentDto;
import ru.practicum.ewm.comments.model.dto.NewCommentDto;
import ru.practicum.ewm.comments.service.CommentPrivateService;
import ru.practicum.ewm.utils.Create;
import ru.practicum.ewm.utils.State;
import ru.practicum.ewm.utils.Update;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * <h2>Comments Private API</h2>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments/users/{userId}")
@Validated
public class CommentPrivateController {
    private final CommentPrivateService service;

    /**
     * Добавление пользователем нового комментария к событию.
     */
    @PostMapping
    public CommentDto add(@RequestBody @Validated({Create.class}) NewCommentDto dto,
                          @PathVariable @Positive(message = "The number must be greater then 0") Long userId,
                          @RequestParam(name = "eventId") Long eventId,
                          @RequestParam(name = "anonymous", required = false) Boolean anonymous) {
        log.info("Add new comment from user {} to event {}. Text {}:", userId, eventId, dto.getText());
        return service.add(userId, eventId, dto, anonymous);
    }

    /**
     * Получение списка пользовательских комментариев с возможностью фильтрации по статусу
     */
    @GetMapping
    public List<CommentDto> getAllByUserId(@PathVariable @Positive(message = "The number must be greater then 0")
                                           Long userId,
                                           @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @RequestParam(name = "size", defaultValue = "10") Integer size,
                                           @RequestParam(name = "state", required = false, defaultValue = "PUBLISHED")
                                           State state) {
        log.info("Get all user {} comments from {} size {} with state {}", userId, from, size, state);
        return service.getAllByUserId(userId, from, size, state);
    }

    @PatchMapping("/{commentId}")
    public CommentDto update(@PathVariable @Positive(message = "The number must be greater then 0") Long userId,
                             @PathVariable @Positive(message = "The number must be greater then 0") Long commentId,
                             @RequestBody @Validated({Update.class}) NewCommentDto dto) {
        log.info("Updating comment id={} by user id={}. New comment {}", commentId, userId, dto);
        return service.update(userId, commentId, dto);
    }

    /**
     * Удаление комментария
     */
    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable @Positive(message = "The number must be greater then 0") Long userId,
                       @PathVariable @Positive(message = "The number must be greater then 0") Long commentId) {
        log.info("Deleting comment id={} of user {}", commentId, userId);
        service.delete(userId, commentId);
    }
}
