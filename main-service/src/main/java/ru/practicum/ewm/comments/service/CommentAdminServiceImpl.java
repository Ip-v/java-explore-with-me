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
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;
import ru.practicum.ewm.utils.State;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.ewm.utils.DateFormat.DATE_FORMATTER;
import static ru.practicum.ewm.utils.State.PUBLISHED;
import static ru.practicum.ewm.utils.State.REJECTED;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentAdminServiceImpl implements CommentAdminService {
    private final CommentRepository repository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public void publish(Long id) {
        Comment comment = repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id=%s not found", id)));

        comment.setPublishedOn(LocalDateTime.now());
        comment.setState(PUBLISHED);

        Comment save = repository.save(comment);
        log.info("Comment successfully published {}", save);
    }

    @Override
    @Transactional
    public void reject(Long id) {
        Comment comment = repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id=%s not found", id)));

        comment.setState(REJECTED);

        Comment save = repository.save(comment);
        log.info("Comment successfully rejected {}", save);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Comment comment = repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id=%s not found", id)));
        repository.delete(comment);
        log.info("Comment id={} successfully deleted", id);
    }

    @Override
    @Transactional
    public CommentDto update(Long commentId, CommentDto dto) {
        Comment comment = repository.findById(commentId).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id=%s not found", commentId)));
        if (dto.getText() != null) {
            comment.setText(dto.getText());
        }
        if (dto.getAuthor() != null) {
            User author = userRepository.findById(dto.getAuthor().getId()).orElseThrow(() ->
                    new NotFoundException(String.format("User with id=%s not found", dto.getAuthor().getId())));
            comment.setAuthor(author);
        }
        if (dto.getEventId() != null) {
            Event event = eventRepository.findById(dto.getEventId()).orElseThrow(() ->
                    new NotFoundException(String.format("Event with id=%s not found", dto.getEventId())));
            comment.setEvent(event);
        }
        if (dto.getCreatedOn() != null) {
            comment.setCreatedOn(LocalDateTime.parse(dto.getCreatedOn(), DATE_FORMATTER));
        }
        if (dto.getPublished() != null) {
            comment.setCreatedOn(LocalDateTime.parse(dto.getPublished(), DATE_FORMATTER));
        }
        if (dto.getState() != null) {
            comment.setState(State.valueOf(dto.getState()));
        }

        Comment save = repository.save(comment);
        log.info("Comment id={} successfully updated. New comment: {}", commentId, save);

        return CommentMapper.toCommentDto(save);
    }

    @Override
    public List<CommentDto> getAll(Long[] eventIds, String text, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                   int from, int size) {
        List<BooleanExpression> expression = new ArrayList<>();
        QComment comment = QComment.comment;

        if (eventIds != null) {
            expression.add(comment.event.id.in(eventIds));
        }
        if (text != null) {
            expression.add(comment.text.containsIgnoreCase(text));
        }
        if (rangeStart != null) {
            expression.add(comment.createdOn.after(rangeStart));
        }
        if (rangeEnd != null) {
            expression.add(comment.createdOn.before(rangeEnd));
        }

        Optional<BooleanExpression> searchCriteria = expression.stream().reduce(BooleanExpression::and);
        Pageable pageable = PageRequest.of(from / size, size);

        List<Comment> comments = searchCriteria
                .map(booleanExpression -> repository
                        .findAll(booleanExpression, pageable)
                        .toList())
                .orElseGet(() -> repository
                        .findAll(pageable)
                        .toList());
        log.info("GET comments with search criteria {} returned {} results", searchCriteria, comments.size());

        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getById(Long commentId) {
        final Comment comment = repository.findById(commentId).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id=%s not found", commentId)));

        return CommentMapper.toCommentDto(comment);
    }
}
