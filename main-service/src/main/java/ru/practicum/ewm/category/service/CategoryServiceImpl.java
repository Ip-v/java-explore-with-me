package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис категорий
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Override
    @Transactional
    public CategoryDto update(CategoryDto dto) {
        Category cat = repository.findById(dto.getId()).orElseThrow(() ->
                new NotFoundException(String.format("Category with id=%s was not found.", dto.getId())));
        cat.setName(dto.getName());
        Category save = repository.save(cat);
        return CategoryMapper.toCategoryDto(save);
    }

    @Override
    @Transactional
    public CategoryDto add(CategoryDto dto) {
        Category category = repository.save(CategoryMapper.toCategory(dto));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public void delete(Long catId) {
        Category cat = repository.findById(catId).orElseThrow(() ->
                new NotFoundException(String.format("Category with id=%s was not found.", catId)));
        repository.deleteById(catId);
    }

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        int page = from / size;
        Pageable pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest)
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long catId) {
        Category cat = repository.findById(catId).orElseThrow(() ->
                new NotFoundException(String.format("Category with id=%s was not found.", catId)));
        return CategoryMapper.toCategoryDto(cat);
    }
}
