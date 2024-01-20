package com.quality.app.repository;

import com.quality.app.domain.DailyChats;
import com.quality.app.service.dto.metrics.chats.IChatsMetrics;
import com.quality.app.service.dto.metrics.chats.IChatsMetricsSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DailyChats entity.
 */
@Repository
public interface DailyChatsRepository extends JpaRepository<DailyChats, Long>, JpaSpecificationExecutor<DailyChats> {
    Optional<DailyChats> findByDay(LocalDate localDate);

    @Query(value = """
        SELECT (total_daily_received_chats) AS totalReceivedChats, (total_daily_conversation_chats_time_in_min) AS totalDailyConversationChatsTime, (total_daily_attended_chats) AS totalAttendedChats,
        (total_daily_missed_chats) AS totalMissedChats, (avg_daily_conversation_chats_time_in_min) AS avgConversationChats, day AS metricDate
        FROM qualitydashboard.daily_chats
        WHERE daily_chats.day between ?1 AND ?2""", nativeQuery = true)
    List<IChatsMetrics> getMetricsByDate(Date start, Date finish);

    @Query(value = """
        SELECT (total_daily_received_chats) AS totalReceivedChats, (total_daily_conversation_chats_time_in_min) AS totalDailyConversationChatsTime, (total_daily_attended_chats) AS totalAttendedChats,
        (total_daily_missed_chats) AS totalMissedChats, (avg_daily_conversation_chats_time_in_min) AS avgConversationChats, day AS metricDate
        FROM qualitydashboard.daily_chats
        WHERE daily_chats.day between ?1 AND ?2
        GROUP BY month(daily_chats.day)""", nativeQuery = true)
    List<IChatsMetrics> getMetricsByDateGroupByMonth(Date start, Date finish);

    @Query(value = """
        SELECT SUM(total_daily_received_chats) AS totalReceivedChats, SUM(total_daily_conversation_chats_time_in_min) AS totalDailyConversationChatsTime, SUM(total_daily_attended_chats) AS totalAttendedChats,
        SUM(total_daily_missed_chats) AS totalMissedChats, SUM(avg_daily_conversation_chats_time_in_min) AS avgConversationChats
        FROM qualitydashboard.daily_chats
        WHERE daily_chats.day between ?1 AND ?2""", nativeQuery = true)
    IChatsMetricsSummary getMetricsSummaryByDate(Date start, Date finish);
}
