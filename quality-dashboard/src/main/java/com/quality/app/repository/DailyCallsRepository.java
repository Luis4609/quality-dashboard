package com.quality.app.repository;

import com.quality.app.domain.DailyCalls;
import com.quality.app.service.dto.metrics.DailyCallsMetricsDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DailyCalls entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyCallsRepository extends JpaRepository<DailyCalls, Long>, JpaSpecificationExecutor<DailyCalls> {

    /**
     * Find by day optional.
     *
     * @param localDate the local date
     * @return the optional
     */
    Optional<DailyCalls> findByDay(LocalDate localDate);

    /**
     * Gets monthly total by date range.
     *
     * @param start the start
     * @param end   the end
     * @return the monthly total by date range
     */
    @Query(
        value = """
        SELECT id, DATE_FORMAT(day, '%Y-%m') AS day, SUM(dc.total_daily_received_calls), SUM(dc.total_daily_attended_calls),
        SUM(dc.total_daily_missed_calls), SUM(dc.total_daily_received_calls_external_agent),
        SUM(dc.total_daily_attended_calls_external_agent), SUM(dc.total_daily_received_calls_internal_agent),
        SUM(dc.total_daily_attended_calls_internal_agent), SUM(dc.total_daily_calls_time_in_min),
        SUM(dc.total_daily_calls_time_external_agent_in_min), SUM(dc.total_daily_calls_time_internal_agent_in_min),
        AVG(dc.avg_daily_operation_time_external_agent_in_min), AVG(dc.avg_daily_operation_time_internal_agent_in_min)
        FROM dashboard.daily_calls dc
        WHERE dc.day between ?1 AND ?2
        GROUP BY month(dc.day), year(dc.day)""",
        nativeQuery = true
    )
    List<DailyCalls> getMonthlyTotalByDateRange(Date start, Date end);

    /**
     * Get main calls metrics object [ ].
     *
     * @return the object [ ]
     */
    @Query(
        value = """
        SELECT SUM(total_daily_received_calls) AS TotalReceived,
        SUM(total_daily_attended_calls) AS TotalAttended,
        SUM(total_daily_missed_calls) AS TotalMissed,
        FROM dashboard.daily_calls""",
        nativeQuery = true
    )
    Object[] getMainCallsMetrics();

    /**
     * Gets avg time calls metrics.
     *
     * @return the avg time calls metrics
     */
    @Query(
        value = """
        SELECT AVG(total_daily_calls_time_in_min), AVG(total_daily_calls_time_external_agent_in_min),
        AVG(total_daily_calls_time_internal_agent_in_min), AVG(avg_daily_operation_time_external_agent_in_min),
        AVG(avg_daily_operation_time_internal_agent_in_min)
        FROM dashboard.daily_calls""",
        nativeQuery = true
    )
    Object getAvgTimeCallsMetrics();

    @Query(
        value = """
        SELECT SUM(total_daily_received_calls), SUM(total_daily_attended_calls), SUM(total_daily_missed_calls),
        SUM(total_daily_attended_calls_external_agent), SUM(total_daily_attended_calls_internal_agent)
        FROM dashboard.daily_calls
        WHERE daily_calls.day between ?1 AND ?2""",
        nativeQuery = true
    )
    DailyCallsMetricsDTO getDailyCallMetricsByDate(Date start, Date end);
}
