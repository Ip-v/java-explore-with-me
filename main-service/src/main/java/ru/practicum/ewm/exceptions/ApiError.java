package ru.practicum.ewm.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Сведения об ошибке
 */
@Getter
@Setter
@ToString
public class ApiError {
    //Список стектрейсов или описания ошибок
    private List<String> errors;
    private String message;
    //Общее описание причины ошибки
    private String reason;
    private String status;
    //Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
    private String timestamp;

}
