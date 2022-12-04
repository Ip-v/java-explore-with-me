package ru.practicum.ewm.comments.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class NewCommentDto {
    @NotEmpty(groups = Create.class)
    @Size(max = 1000)
    private String text;
    private Boolean anonymous;
}
