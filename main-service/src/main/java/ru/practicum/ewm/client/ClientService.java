package ru.practicum.ewm.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.client.dto.EndPointHit;

import java.time.LocalDateTime;

import static ru.practicum.ewm.utils.DateFormat.DATE_FORMATTER;

@Slf4j
@Service
@AllArgsConstructor
public class ClientService {
    private final Client client;

    public void hit(String uri, String ip) {
        EndPointHit endPointHit = EndPointHit.builder()
                .ip(ip)
                .app("ewm-main-service")
                .timestamp(DATE_FORMATTER.format(LocalDateTime.now()))
                .uri(uri)
                .build();
        client.hit(endPointHit);
        log.info("Client request {}", endPointHit);
    }

    public Long getStats(String uri) {
        //todo implement
        return null;
    }
}
