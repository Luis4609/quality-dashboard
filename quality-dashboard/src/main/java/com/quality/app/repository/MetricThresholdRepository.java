package com.quality.app.repository;

import com.quality.app.domain.MetricThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MetricThreshold entity.
 */
@Repository
public interface MetricThresholdRepository extends JpaRepository<MetricThreshold, Long>, JpaSpecificationExecutor<MetricThreshold> {
    List<MetricThreshold> findByEntityName(String name);
}
