package ru.practicum.ewm.comments.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.comments.model.CommentMapper;
import ru.practicum.ewm.comments.model.QComment;
import ru.practicum.ewm.comments.model.dto.CommentDto;
import ru.practicum.ewm.comments.repository.CommentRepository;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.AccessDeniedException;
import ru.practicum.ewm.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.utils.State.PUBLISHED;

/**
 * Comment Service for public API
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentPublicServiceImpl implements CommentPublicService {
    private final CommentRepository repository;
    private final EventRepository eventRepository;

    @Override
    public List<CommentDto> getAllByEventId(Long eventId, String text, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                            int from, int size) {

        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException(String.format("Event with id=%s not found", eventId));
        }

        List<BooleanExpression> expression = new ArrayList<>();
        QComment comment = QComment.comment;

        expression.add(comment.event.id.eq(eventId));
        if (text != null) {
            expression.add(comment.text.containsIgnoreCase(text));
        }
        if (rangeStart != null) {
            expression.add(comment.createdOn.after(rangeStart));
        }
        if (rangeEnd != null) {
            expression.add(comment.createdOn.before(rangeEnd));
        }

        expression.add(comment.state.eq(PUBLISHED));

        BooleanExpression searchCriteria = expression.stream().reduce(BooleanExpression::and).get();
        Pageable pageable = PageRequest.of(from / size, size);

        List<Comment> comments = repository.findAll(searchCriteria, pageable).toList();
        log.info("Get comments with search criteria {} returned {} results", searchCriteria, comments.size());

        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getById(Long id) {
        final Comment comment = repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", id)));

        if (!comment.getState().equals(PUBLISHED)) {
            throw new AccessDeniedException("Comment isn't published yet");
        }
        log.info("Comment id={} request", id);

        return CommentMapper.toCommentDto(comment);
    }
}
