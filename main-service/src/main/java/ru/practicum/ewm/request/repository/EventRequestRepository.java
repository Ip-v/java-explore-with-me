package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.compilation.model.Compilation;

public interface EventRequestRepository extends JpaRepository<Compilation, Integer> {
}
