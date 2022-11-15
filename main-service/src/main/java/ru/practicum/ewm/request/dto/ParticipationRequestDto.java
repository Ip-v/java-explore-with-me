package ru.practicum.ewm.request.dto;

/**
 * Заявка на участие в событии
 */
public class ParticipationRequestDto {
    //022-09-06T21:10:05.432
    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private String status; //PENDING
}
