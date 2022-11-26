package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.dto.CategoryDto;

import java.util.List;

/**
 * Интерфейс сервиса категорий
 */
public interface CategoryService {
    /**
     * Изменение категории<br>
     * <i>Имя категории должно быть уникальным</i>
     */
    CategoryDto updateCategory(CategoryDto dto);

    /**
     * Добавление новой категории<br>
     * <i>Имя категории должно быть уникальным</i>
     */
    CategoryDto addCategory(CategoryDto dto);

    /**
     * Удаление категории
     */
    void deleteCategory(Long catId);

    /**
     * Получение категорий
     */
    List<CategoryDto> getAll(Integer from, Integer size);

    /**
     * Получение информации о категории по ее идентификатору
     */
    CategoryDto getById(Long catId);
}
