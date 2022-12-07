package ru.practicum.ewm.comments.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.utils.Create;
import ru.practicum.ewm.utils.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class NewCommentDto {
    @NotEmpty(groups = {Create.class, Update.class})
    @Size(groups = {Create.class, Update.class}, max = 1000)
    private String text;
    private Boolean anonymous;
}
