package ru.practicum.ewm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.compilation.model.Compilation;

public interface UserRepository extends JpaRepository<Compilation, Integer> {
}
