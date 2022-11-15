package ru.practicum.ewm.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.compilation.model.Compilation;

public interface EventRepository extends JpaRepository<Compilation, Integer> {
}
