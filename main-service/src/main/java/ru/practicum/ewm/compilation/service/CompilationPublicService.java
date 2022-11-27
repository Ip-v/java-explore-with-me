package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationDto;

import java.util.List;

/**
 * Интерфейс службы подборок
 */
public interface CompilationPublicService {
    /**
     * Поулчение подборок событий
     */
    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    /**
     * Получение подборки событий по его id
     *
     * @param compId ид
     * @return CompilationDto или null
     */
    CompilationDto getCompilationById(Long compId);
}
