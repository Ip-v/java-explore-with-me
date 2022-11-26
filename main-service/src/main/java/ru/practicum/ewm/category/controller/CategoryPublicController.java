package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Публичный API для работы с категориями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/categories")
public class CategoryPublicController {
    private final CategoryService service;

    /**
     * Получение категорий
     */
    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(name = "from", defaultValue = "10") Integer from,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Запрос списка категорий from {} size {}", from, size);
        return service.getAll(from, size);
    }

    /**
     * Получение информации о категории по ее идентификатору
     */
    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable(name = "catId") @NotNull Long catId) {
        log.info("Запрос категорий по ИД {}", catId);
        return service.getById(catId);
    }
}
