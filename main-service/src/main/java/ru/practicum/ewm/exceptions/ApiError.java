package ru.practicum.ewm.exceptions;

import lombok.*;

/**
 * Сведения об ошибке
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {
    //Список стектрейсов или описания ошибок
    private String errors;
    private String message;
    //Общее описание причины ошибки
    private String reason;
    private String status;
    //Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
    private String timestamp;
}
