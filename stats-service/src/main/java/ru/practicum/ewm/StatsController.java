package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EndPointHit;
import ru.practicum.ewm.dto.ViewStats;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService service;

    /**
     * Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
     * Название сервиса, uri и ip пользователя указаны в теле запроса.
     */
    @PostMapping("/hit")
    public void hit(@RequestBody EndPointHit hit) {
        log.info("Saving hit {}", hit);
        service.hit(hit);
    }

    /**
     * Получение статистики по посещениям.<br>
     * <i>Значение латы и времени закодировано</i>
     */
    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam(value = "start") String start,
                                    @RequestParam(value = "end") String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Statistics request");
        return service.getStats(start, end, uris, unique);
    }
}
