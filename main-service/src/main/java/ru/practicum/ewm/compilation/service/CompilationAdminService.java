package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;

/**
 * Сервис подборок
 */
public interface CompilationAdminService {

    /**
     * Добавление новой подборки
     */
    CompilationDto add(NewCompilationDto dto);

    /**
     * Удаление события из подборки
     */
    void delete(Long compId);

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
    void pin(Long compId);

    /**
     * Открепить подборку на главной страницу
     */
    void unpin(Long compId);
}
