package com.quality.app.service;

import com.quality.app.domain.*; // for static metamodels
import com.quality.app.domain.MetricThreshold;
import com.quality.app.repository.MetricThresholdRepository;
import com.quality.app.service.criteria.MetricThresholdCriteria;
import com.quality.app.service.dto.MetricThresholdDTO;
import com.quality.app.service.mapper.MetricThresholdMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MetricThreshold} entities in the database.
 * The main input is a {@link MetricThresholdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MetricThresholdDTO} or a {@link Page} of {@link MetricThresholdDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MetricThresholdQueryService extends QueryService<MetricThreshold> {

    private final Logger log = LoggerFactory.getLogger(MetricThresholdQueryService.class);

    private final MetricThresholdRepository metricThresholdRepository;

    private final MetricThresholdMapper metricThresholdMapper;

    public MetricThresholdQueryService(MetricThresholdRepository metricThresholdRepository, MetricThresholdMapper metricThresholdMapper) {
        this.metricThresholdRepository = metricThresholdRepository;
        this.metricThresholdMapper = metricThresholdMapper;
    }

    /**
     * Return a {@link List} of {@link MetricThresholdDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MetricThresholdDTO> findByCriteria(MetricThresholdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MetricThreshold> specification = createSpecification(criteria);
        return metricThresholdMapper.toDto(metricThresholdRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MetricThresholdDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MetricThresholdDTO> findByCriteria(MetricThresholdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MetricThreshold> specification = createSpecification(criteria);
        return metricThresholdRepository.findAll(specification, page).map(metricThresholdMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MetricThresholdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MetricThreshold> specification = createSpecification(criteria);
        return metricThresholdRepository.count(specification);
    }

    /**
     * Function to convert {@link MetricThresholdCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MetricThreshold> createSpecification(MetricThresholdCriteria criteria) {
        Specification<MetricThreshold> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MetricThreshold_.id));
            }
            if (criteria.getEntityName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEntityName(), MetricThreshold_.entityName));
            }
            if (criteria.getMetricName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMetricName(), MetricThreshold_.metricName));
            }
            if (criteria.getSuccessPercentage() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSuccessPercentage(), MetricThreshold_.successPercentage));
            }
            if (criteria.getDangerPercentage() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDangerPercentage(), MetricThreshold_.dangerPercentage));
            }
        }
        return specification;
    }
}
