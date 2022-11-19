package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.utils.Create;
import ru.practicum.ewm.utils.Update;

import javax.validation.constraints.NotNull;

/**
 * API администратора для работы с категориями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {
    private final CategoryService service;

    /**
     * Изменение категории<br>
     * <i>Обратите внимание: имя категории должно быть уникальным</i>
     */
    @PatchMapping
    public CategoryDto updateCategory(@RequestBody @Validated({Update.class}) CategoryDto dto) {
        log.info("Изменение категории {}", dto);
        //todo implement
        return null;
    }

    /**
     * Добавление новой категории<br>
     * <i>Обратите внимание: имя категории должно быть уникальным</i>
     */
    @PutMapping
    public CategoryDto addCategory(@RequestBody @Validated({Create.class}) CategoryDto dto) {
        log.info("Изменение категории {}", dto);
        //todo implement
        return null;
    }

    /**
     * Удаление категории
     */
    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable(name = "catId") @NotNull Long catId) {
        log.info("Удаление категории {}", catId);
        //todo implement
    }
}
