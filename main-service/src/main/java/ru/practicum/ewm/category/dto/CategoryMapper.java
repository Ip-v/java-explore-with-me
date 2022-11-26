package ru.practicum.ewm.category.dto;

import ru.practicum.ewm.category.model.Category;

/**
 * Category mapper
 */
public class CategoryMapper {

    /**
     * CategoryDto -> Category
     */
    public static Category toCategory(CategoryDto dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    /**
     * CategoryDto -> Category
     */
    public static CategoryDto toCategoryDto(Category c) {
        return CategoryDto.builder()
                .id(c.getId())
                .name(c.getName())
                .build();
    }
}
