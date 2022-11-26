package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;

import java.util.List;

/**
 * Сервис подборок
 */
public interface CompilationAdminService {

    /**
     * Добавление новой подборки
     */
    CompilationDto addCompilation(NewCompilationDto dto);

    /**
     * Удаление события из подборки
     */
    void deleteCompilation(Long compId);

    /**
     * Получение подборки событий по его id
     *
     * @param compId ид
     * @return CompilationDto или null
     */
    CompilationDto getCompilationById(Long compId);

    /**
     * Поулчение подборок событий
     */
    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    /**
     * Добавление события в подборку
     */
    void addEventToCompilation(Long compId, Long eventId);

    /**
     * Удаление события из подборки
     */
    void deleteEventFromCompilation(Long compId, Long eventId);

    /**
     * Закрепить подборку на главной странице
     */
    void pinCompilation(Long compId);

    /**
     * Открепить подборку на главной страницу
     */
    void unpinCompilation(Long compId);
}
