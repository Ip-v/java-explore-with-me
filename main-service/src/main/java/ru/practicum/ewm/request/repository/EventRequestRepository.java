package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;

import java.util.List;

/**
 * Репозитория запросов
 */
@EnableJpaRepositories
public interface EventRequestRepository extends JpaRepository<Request, Long>, QuerydslPredicateExecutor<Event> {
    int countByEventAndConfirmed(Event event, RequestStatus confirmed);

    /**
     * Запрос заявок на участие пользвоателя в чужих событиях
     */
    @Query("select req from Request req left join Event as ev on req.event.id = ev.id " +
            "where req.user.id <> ev.initiator.id order by req.createdOn desc")
    List<Request> getAllUserRequests(Long userId);

    List<Request> findByEvent(Event event);

    @Query("update Request set confirmed = 'REJECTED' where event.id =?1")
    void rejectAllPendingRequests(Long eventId);
}
