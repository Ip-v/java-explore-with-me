package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationMapper;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.ConditionsAreNotMetException;
import ru.practicum.ewm.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис API администратора для подборок
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationAdminServiceImpl implements CompilationAdminService {

    private final CompilationRepository repository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CompilationDto addCompilation(NewCompilationDto dto) {
        List<Event> events = eventRepository.findAllById(dto.getEvents());
        Compilation compilation = Compilation.builder()
                .events(events)
                .pinned(dto.getPinned())
                .title(dto.getTitle())
                .build();

        Compilation save = repository.save(compilation);
        log.info("Compilation id={} successfully", save.getId());
        return CompilationMapper.toCompilationDto(save);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation %d not found", compId)));
        repository.delete(compilation);
        log.info("Compilation {} successfully deleted", compId); //todo check
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation %d not found", compId)));
        log.info("Compilation {} successfully found", compId);
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of(from / size, size);
        List<Compilation> list = pinned == null ? repository.findAll(pageRequest).getContent()
                : pinned ? repository.findCompilationsByPinnedTrue(pageRequest)
                : repository.findCompilationsByPinnedFalse(pageRequest);
        log.info("Compilations found {} successfully found", list.size());
        return list.stream().map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = repository.findById(compId).orElseThrow(() ->
                new NotFoundException(String.format("Compilation id=%d not found", compId)));

        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event id=%d not found", eventId)));

        if(compilation.getEvents().contains(event)) {
            throw new ConditionsAreNotMetException(
                    String.format("Compilation %d already contains event %d", compId, eventId));
        }

        compilation.getEvents().add(event);
        repository.save(compilation);
        log.info("Event {} added to compilation {}", eventId, compId);
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        Compilation compilation = repository.findById(compId).orElseThrow(() ->
                new NotFoundException(String.format("Compilation id=%d not found", compId)));

        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event id=%d not found", eventId)));

        if(!compilation.getEvents().contains(event)) {
            throw new ConditionsAreNotMetException(
                    String.format("Compilation %d doesn't contains event %d", compId, eventId));
        }

        compilation.getEvents().remove(event);
        repository.save(compilation);
        log.info("Event {} deleted from compilation {}", eventId, compId);
    }

    @Override
    @Transactional
    public void pinCompilation(Long compId) {
        Compilation compilation = repository.findById(compId).orElseThrow(() ->
                new NotFoundException(String.format("Compilation id=%d not found", compId)));

        compilation.setPinned(true);
        repository.save(compilation);
        log.info("Compilation id={} pinned", compId);
    }

    @Override
    @Transactional
    public void unpinCompilation(Long compId) {
        Compilation compilation = repository.findById(compId).orElseThrow(() ->
                new NotFoundException(String.format("Compilation id=%d not found", compId)));

        compilation.setPinned(false);
        repository.save(compilation);
        log.info("Compilation id={} unpinned", compId);
    }
}
