package ru.practicum.ewm.comments.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.user.dto.UserShortDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class CommentDto {
    private Long id;
    @NotEmpty
    @Size(max = 1000)
    private String text;
    @NotNull
    private UserShortDto author;
    private Long eventId;
    @NotNull
    private String createdOn;
    private String published;
    @NotNull
    private String state;
}
