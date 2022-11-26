package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;

/**
 * Репозитория запросов
 */
@EnableJpaRepositories
public interface EventRequestRepository extends JpaRepository<Request, Long> {
//    @Query("SELECT req FROM Request req WHERE req.event.id =?1 and req.confirmed = ?2")
//    Integer countParticipants(Long event, RequestStatus status); //todo delete

    Long countByEventAndConfirmed(Event event, RequestStatus confirmed);
}
