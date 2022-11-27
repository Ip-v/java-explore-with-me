package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationPublicService;

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

    private final CompilationPublicService service;

    /**
     * Поулчение подборок событий
     */
    @GetMapping
    public List<CompilationDto> getAll(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                       @RequestParam(name = "from", defaultValue = "0") Integer from,
                                       @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Запрос подборки pinned {} from {} size {}", pinned, from, size);
        return service.getCompilations(pinned, from, size);
    }

    /**
     * Получение подборки событий по его id
     */
    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable(name = "compId") @NotNull Long compId) {
        log.info("Запрос подборки по ид {}", compId);
        return service.getCompilationById(compId);
    }
}
