package ru.practicum.ewm.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.client.dto.EndPointHit;
import ru.practicum.ewm.client.dto.ViewStats;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.practicum.ewm.utils.DateFormat.DATE_FORMATTER;

/**
 * Клиент сервера статистики
 */
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
        String start = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC).format(DATE_FORMATTER);
        String end = LocalDateTime.now().plusYears(10).format(DATE_FORMATTER);
        List<ViewStats> views = client.getStats(start, end, List.of(uri), false);
        log.info("Retrieving statistic for uri={}", uri);

        return views != null && views.size() > 0 ? views.get(0).getHits() : 0L;
    }

    public Map<String, Long> getUrisWithHits(List<Event> events) {
        String start = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC).format(DATE_FORMATTER);
        String end = LocalDateTime.now().plusYears(10).format(DATE_FORMATTER);

        List<String> uris = new ArrayList<>();
        events.forEach(e -> uris.add("/events/" + e.getId()));
        List<ViewStats> views = client.getStats(start, end, uris, false);
        log.info("Retrieving statistic for uri={}", events);

        Map<String, Long> map = new HashMap<>();
        views.forEach(v -> map.put(v.getUri(), v.getHits()));

        return map;
    }
}
