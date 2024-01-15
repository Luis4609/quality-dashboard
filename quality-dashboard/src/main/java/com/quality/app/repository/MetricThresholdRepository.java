package com.quality.app.repository;

import com.quality.app.domain.MetricThreshold;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MetricThreshold entity.
 */
@Repository
public interface MetricThresholdRepository extends JpaRepository<MetricThreshold, Long>, JpaSpecificationExecutor<MetricThreshold> {}
