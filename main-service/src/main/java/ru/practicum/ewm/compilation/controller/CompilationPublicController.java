package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Контроллер подборок событий
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
@Validated
public class CompilationPublicController {

    private final CompilationService service;

    /**
     * Поулчение подборок событий
     */
    @GetMapping
    public List<CompilationDto> getAll(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                       @RequestParam(name = "from", defaultValue = "10") Integer from,
                                       @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Запрос подборки pinned {} from {} size {}", pinned, from, size);
        //todo implement
        return null;
    }

    /**
     * Получение подборки событий по его id
     *
     * @param compId ид
     * @return CompilationDto или null
     */
    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable(name = "compId") @NotNull Long compId) {
        //todo implement
        log.info("Запрос подборки по ид {}", compId);
        return null;
    }
}
