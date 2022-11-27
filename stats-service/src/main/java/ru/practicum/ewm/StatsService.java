package ru.practicum.ewm;

import ru.practicum.ewm.dto.EndPointHit;
import ru.practicum.ewm.dto.ViewStats;

import java.util.List;

public interface StatsService {

    /**
     * Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
     * Название сервиса, uri и ip пользователя указаны в теле запроса.
     */
    void hit(EndPointHit hit);

    /**
     * Получение статистики по посещениям.<br>
     * <i>Значение латы и времени закодировано</i>
     */
    List<ViewStats> getStats(String start, String end, List<String> uris, boolean unique);
}
