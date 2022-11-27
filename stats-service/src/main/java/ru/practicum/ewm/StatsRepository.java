package ru.practicum.ewm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.dto.ViewStats;
import ru.practicum.ewm.model.Statistic;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий статистики
 */
@Repository
public interface StatsRepository extends JpaRepository<Statistic, Long> {

    @Query("select new ru.practicum.ewm.dto.ViewStats(s.app, s.uri, count(s.ip)) " +
            "from Statistic as s " +
            "where s.createdOn between :start and :end and (coalesce(:uris, null) is null or s.uri in :uris) " +
            "GROUP BY s.uri, s.app")
    List<ViewStats> getStats(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                     @Param("uris") List<String> uris);

    @Query("select new ru.practicum.ewm.dto.ViewStats(s.app, s.uri, count(distinct s.ip)) " +
            "from Statistic as s " +
            "where s.createdOn between :start and :end " +
            "and (coalesce(:uris, null) is null or s.uri in :uris) " +
            "GROUP BY s.uri, s.app")
    List<ViewStats> getStatsUniqueIp(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                             @Param("uris") List<String> uris);
}
