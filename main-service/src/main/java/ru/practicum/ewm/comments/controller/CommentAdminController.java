package ru.practicum.ewm.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.model.dto.CommentDto;
import ru.practicum.ewm.comments.service.CommentAdminService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <h2>Comments Admin API</h2>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments/admin")
@Validated
public class CommentAdminController {
    private final CommentAdminService service;

    /**
     * Публикация комментария
     */
    @PatchMapping("/{commentId}/publish")
    public void publish(@PathVariable(name = "commentId") @Positive Long commentId) {
        log.info("Publish comment id={}", commentId);
        service.publish(commentId);
    }

    /**
     * Отклонение комментария
     */
    @PatchMapping("/{commentId}/reject")
    public void reject(@PathVariable(name = "commentId") @Positive Long commentId) {
        log.info("Reject comment id={}", commentId);
        service.reject(commentId);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable(name = "commentId") @Positive Long commentId) {
        log.info("Delete comment id={}", commentId);
        service.delete(commentId);
    }

    /**
     * Обновление пользовательского комментария
     */
    @PatchMapping("/{commentId}")
    public CommentDto update(@PathVariable(name = "commentId") @Positive Long commentId,
                             @RequestBody @Validated CommentDto dto) {
        log.info("Updating comment id = {} with new data {}", commentId, dto);
        return service.update(commentId, dto);
    }

    /**
     * Поиск комментариев с возможностью фильтрации
     */
    @GetMapping
    public List<CommentDto> getAllByEventId(@RequestParam(name = "eventId", required = false) Long[] eventIds,
                                            @RequestParam(name = "text", required = false) String text,
                                            @RequestParam(name = "rangeStart", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam(name = "rangeEnd", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam(name = "from", defaultValue = "0")
                                            @PositiveOrZero Integer from,
                                            @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size) {
        log.info("Request comments for event{} with filter {} start {} end {} from {} size {}",
                eventIds, text, rangeStart, rangeEnd, from, size);
        return service.getAll(eventIds, text, rangeStart, rangeEnd, from, size);
    }
}
