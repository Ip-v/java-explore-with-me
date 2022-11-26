package ru.practicum.ewm.compilation.model;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.event.model.EventMapper;

import java.util.stream.Collectors;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation c) {
        return CompilationDto.builder()
                .events(c.getEvents().stream().map(EventMapper::toEventShortDto).collect(Collectors.toList()))
                .id(c.getId())
                .pinned(c.getPinned())
                .title(c.getTitle())
                .build();
    }
}
