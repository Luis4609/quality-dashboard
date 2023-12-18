package com.quality.app.repository;

import com.quality.app.domain.DailyCalls;
import com.quality.app.domain.DailyChats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DailyChats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyChatsRepository extends JpaRepository<DailyChats, Long>, JpaSpecificationExecutor<DailyChats> {
    Optional<DailyChats> findByDay(LocalDate localDate);
}
