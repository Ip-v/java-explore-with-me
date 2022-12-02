package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.EndPointHit;
import ru.practicum.ewm.dto.ViewStats;
import ru.practicum.ewm.model.Statistic;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public void hit(EndPointHit hit) {
        Statistic s = Statistic.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .createdOn(LocalDateTime.parse(hit.getTimestamp(), dateTimeFormatter))
                .build();
        repository.save(s);
        log.info("Hit successfully saved to repository {}", hit);
    }

    @Override
    public List<ViewStats> getStats(String start, String end, List<String> uris, boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(end, dateTimeFormatter);

        if (unique) {
            return repository.getStatsUniqueIp(startTime, endTime, uris);
        } else {
            return repository.getStats(startTime, endTime, uris);
        }

    }

    private LocalDateTime encode(String date) {
        String encoded = URLEncoder.encode(date, Charset.defaultCharset());
        return LocalDateTime.parse(encoded, dateTimeFormatter);
    }
}
