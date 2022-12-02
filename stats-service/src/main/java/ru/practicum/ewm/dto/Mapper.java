package ru.practicum.ewm.dto;

import ru.practicum.ewm.model.Statistic;

public class Mapper {
    public static ViewStats toViewStats(Statistic statistic, Long hits) {
        return ViewStats.builder()
                .app(statistic.getApp())
                .uri(statistic.getUri())
                .hits(hits)
                .build();
    }
}
