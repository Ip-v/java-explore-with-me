package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationMapper;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Служба подборок
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationPublicServiceImpl implements CompilationPublicService {
    private final CompilationRepository repository;

    @Override
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of(from / size, size);
        List<Compilation> list;
        if (pinned == null) {
            list = repository.findAll(pageRequest).getContent();
        } else if (pinned) {
            list = repository.findCompilationsByPinnedTrue(pageRequest);
        } else {
            list = repository.findCompilationsByPinnedFalse(pageRequest);
        }
        log.info("Compilations found {} successfully found", list.size());

        return list
                .stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getById(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation %d not found", compId)));
        log.info("Compilation {} successfully found", compId);

        return CompilationMapper.toCompilationDto(compilation);
    }
}
