package ru.practicum.ewm.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.model.dto.CommentDto;
import ru.practicum.ewm.comments.service.CommentPublicService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <h2>Comments Public API</h2>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
@Validated
public class CommentPublicController {
    private final CommentPublicService service;

    /**
     * Получение опубликованных комментариев к событию с возможностью фильтрации
     */
    @GetMapping("/{eventId}")
    public List<CommentDto> getAllByEventId(@PathVariable(name = "eventId") @Positive Long eventId,
                                            @RequestParam(name = "text", required = false) String text,
                                            @RequestParam(name = "rangeStart", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam(name = "rangeEnd", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam(name = "from", defaultValue = "0")
                                            @PositiveOrZero Integer from,
                                            @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size) {
        log.info("GET comments for event id={} with filter text={} start={} end={} from={} size={}",
                eventId, text, rangeStart, rangeEnd, from, size);
        return service.getAllByEventId(eventId, text, rangeStart, rangeEnd, from, size);
    }

    /**
     * Получение опубликованного комментария по его id
     */
    @GetMapping
    public CommentDto getById(@RequestParam(name = "commentId") @Positive Long commentId) {
        log.info("GET comment by id={}:", commentId);
        return service.getById(commentId);
    }
}
