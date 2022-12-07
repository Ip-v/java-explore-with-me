package ru.practicum.ewm.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.comments.model.CommentMapper;
import ru.practicum.ewm.comments.model.dto.CommentDto;
import ru.practicum.ewm.comments.model.dto.NewCommentDto;
import ru.practicum.ewm.comments.repository.CommentRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.AccessDeniedException;
import ru.practicum.ewm.exceptions.ConditionsAreNotMetException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;
import ru.practicum.ewm.utils.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.ewm.utils.State.*;

/**
 * Comment Service for private API
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentPrivateServiceImpl implements CommentPrivateService {
    private final CommentRepository repository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CommentDto add(Long userId, Long eventId, NewCommentDto dto, Boolean anonymous) {
        final User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));

        final Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%s not found", eventId)));

        if (!event.getState().equals(PUBLISHED)) {
            throw new ConditionsAreNotMetException(String.format("Wrong event %d state", eventId));
        }

        if (!userId.equals(event.getInitiator().getId())) {
            Optional<Request> request = event.getRequests()
                    .stream()
                    .filter(r -> r.getUser().equals(user) && r.getConfirmed().equals(CONFIRMED))
                    .findFirst();
            if (request.isEmpty()) {
                throw new ConditionsAreNotMetException("No request found or request wasn't confirmed");
            }
        }

        Comment comment = Comment.builder()
                .text(dto.getText())
                .author(user)
                .event(event)
                .createdOn(LocalDateTime.now())
                .anonymous(anonymous != null && anonymous)
                .state(PENDING)
                .build();
        Comment save = repository.save(comment);
        log.info("Saved new comment {}", save);

        return CommentMapper.toCommentDto(save);
    }

    @Override
    public List<CommentDto> getAllByUserId(Long userId, int from, int size, State state) {
        final User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%s not found", userId)));

        Pageable pageRequest = PageRequest.of(from / size, size);
        List<Comment> comments = repository.findAllByAuthorAndState(user, state, pageRequest);
        log.info("All user {} comments request {}", userId, comments);

        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long userId, Long commentId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id=%s not found", userId));
        }

        final Comment comment = repository.findById(commentId).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id=%s not found", commentId)));

        if (!comment.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException("Only author or admin can delete comment.");
        }

        repository.delete(comment);
        log.info("Comment id={} successfully deleted", commentId);
    }

    @Override
    @Transactional
    public CommentDto update(Long userId, Long commentId, NewCommentDto dto) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id=%s not found", userId));
        }

        final Comment comment = repository.findById(commentId).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id=%s not found", commentId)));

        if (dto.getText() != null) {
            comment.setText(dto.getText());
        }
        if (comment.getAnonymous() != null) {
            comment.setAnonymous(dto.getAnonymous());
        }

        Comment save = repository.save(comment);
        log.info("Successfully update comment {}", save);

        return CommentMapper.toCommentDto(save);
    }
}
