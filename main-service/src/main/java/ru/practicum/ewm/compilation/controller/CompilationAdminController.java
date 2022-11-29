package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.service.CompilationAdminService;
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

    private final CompilationAdminService service;

    /**
     * Добавление новой подборки
     */
    @PostMapping
    public CompilationDto add(@RequestBody @Validated({Create.class}) NewCompilationDto dto) {
        log.info("Added new compilation {}", dto);
        return service.add(dto);
    }

    /**
     * Удаление подборки
     */
    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable(name = "compId") Long compId) {
        log.info("Compilation id={} deleted", compId);
        service.delete(compId);
    }

    /**
     * Удаление события из подборки
     */
    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable(name = "compId") Long compId,
                            @PathVariable(name = "eventId") Long eventId) {
        log.info("Delete from compilation {} event {}", compId, eventId);
        service.deleteEventFromCompilation(compId, eventId);
    }

    /**
     * Добавление события в подборку
     */
    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable(name = "compId") Long compId,
                                      @PathVariable(name = "eventId") Long eventId) {
        log.info("Added to compilation {} event {}", compId, eventId);
        service.addEventToCompilation(compId, eventId);
    }

    /**
     * Открепить подборку на главной страницу
     */
    @DeleteMapping("/{compId}/pin")
    public void unpin(@PathVariable(name = "compId") Long compId) {
        log.info("Unpin compilation {}", compId);
        service.unpin(compId);
    }

    /**
     * Закрепить подборку на главной странице
     */
    @PatchMapping("/{compId}/pin")
    public void pin(@PathVariable(name = "compId") Long compId) {
        log.info("Pin compilation {}", compId);
        service.pin(compId);
    }
}
