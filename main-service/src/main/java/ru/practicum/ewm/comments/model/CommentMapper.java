package ru.practicum.ewm.comments.model;

import ru.practicum.ewm.comments.model.dto.CommentDto;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.dto.UserShortDto;

import static ru.practicum.ewm.utils.DateFormat.DATE_FORMATTER;

/**
 * Comment Mapper
 */
public class CommentMapper {

    /**
     * Comment -> CommentDto
     */
    public static CommentDto toCommentDto(Comment comment) {
        UserShortDto author = comment.getAnonymous() ? null : UserMapper.toUserShortDto(comment.getAuthor());
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .author(author)
                .eventId(comment.getEvent().getId())
                .createdOn(DATE_FORMATTER.format(comment.getCreatedOn()))
                .published(comment.getPublishedOn() == null ? null : DATE_FORMATTER.format(comment.getPublishedOn()))
                .state(comment.getState().name())
                .build();
    }
}
