package ru.practicum.ewm.client.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class EndPointHit {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
