package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;
import ru.practicum.ewm.utils.Create;

/**
 * <h2>API администратора для работы с подборками событий</h2>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/compilations")
public class CompilationAdminController {

    private final CompilationService service;

    /**
     * Добавление новой подборки
     */
    @PostMapping
    public CompilationDto addCompilation(@RequestBody @Validated({Create.class}) CompilationDto dto) {
        log.info("Добавление новой подюлоки {}", dto);
        //todo implement
        return null;
    }

    /**
     * Удаление подборки
     */
    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable(name = "compId") Long compId) {
        log.info("Удаление подборки {}", compId);
        //TODO implement
    }

    /**
     * Удаление события из подборки
     */
    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable(name = "compId") Long compId,
                            @PathVariable(name = "eventId") Long eventId) {
        log.info("Удаление из подборки {} события {}", compId, eventId);
        //TODO implement
    }

    /**
     * Добавление события в подборку
     */
    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable(name = "compId") Long compId,
                         @PathVariable(name = "eventId") Long eventId) {
        log.info("Добавление в подборку {} события {}", compId, eventId);
        //TODO implement
    }

    /**
     * Открепить подборку на главной страницу
     */
    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable(name = "compId") Long compId) {
        log.info("Открепить подборку {} на главной страницу", compId);
        //TODO implement
    }

    /**
     * Закрепить подборку на главной странице
     */
    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable(name = "compId") Long compId) {
        log.info("Закрепить подборку {} на главной странице", compId);
        //TODO implement
    }
}
