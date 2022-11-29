package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.utils.State;

import java.util.List;

/**
 * Репозитория запросов
 */
@EnableJpaRepositories
public interface EventRequestRepository extends JpaRepository<Request, Long>, QuerydslPredicateExecutor<Event> {
    int countByEventAndConfirmed(Event event, State confirmed);

    /**
     * Запрос заявок на участие пользвоателя в чужих событиях
     */
    @Query("SELECT req FROM Request req LEFT JOIN Event AS ev ON req.event.id = ev.id " +
            "WHERE req.user.id <> ev.initiator.id ORDER BY req.createdOn DESC")
    List<Request> getAllUserRequests(Long userId);

    List<Request> findByEvent(Event event);

    @Query("UPDATE Request SET confirmed = 'REJECTED' WHERE event.id =?1")
    void rejectAllPendingRequests(Long eventId);
}
