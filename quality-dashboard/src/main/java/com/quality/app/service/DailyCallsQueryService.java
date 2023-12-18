package com.quality.app.service;

import com.quality.app.domain.*; // for static metamodels
import com.quality.app.domain.DailyCalls;
import com.quality.app.repository.DailyCallsRepository;
import com.quality.app.service.criteria.DailyCallsCriteria;
import com.quality.app.service.dto.DailyCallsDTO;
import com.quality.app.service.mapper.DailyCallsMapper;
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
 * Service for executing complex queries for {@link DailyCalls} entities in the database.
 * The main input is a {@link DailyCallsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DailyCallsDTO} or a {@link Page} of {@link DailyCallsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DailyCallsQueryService extends QueryService<DailyCalls> {

    private final Logger log = LoggerFactory.getLogger(DailyCallsQueryService.class);

    private final DailyCallsRepository dailyCallsRepository;

    private final DailyCallsMapper dailyCallsMapper;

    public DailyCallsQueryService(DailyCallsRepository dailyCallsRepository, DailyCallsMapper dailyCallsMapper) {
        this.dailyCallsRepository = dailyCallsRepository;
        this.dailyCallsMapper = dailyCallsMapper;
    }

    /**
     * Return a {@link List} of {@link DailyCallsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DailyCallsDTO> findByCriteria(DailyCallsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DailyCalls> specification = createSpecification(criteria);
        return dailyCallsMapper.toDto(dailyCallsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DailyCallsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DailyCallsDTO> findByCriteria(DailyCallsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DailyCalls> specification = createSpecification(criteria);
        return dailyCallsRepository.findAll(specification, page).map(dailyCallsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DailyCallsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DailyCalls> specification = createSpecification(criteria);
        return dailyCallsRepository.count(specification);
    }

    /**
     * Function to convert {@link DailyCallsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DailyCalls> createSpecification(DailyCallsCriteria criteria) {
        Specification<DailyCalls> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DailyCalls_.id));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDay(), DailyCalls_.day));
            }
            if (criteria.getTotalDailyReceivedCalls() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalDailyReceivedCalls(), DailyCalls_.totalDailyReceivedCalls));
            }
            if (criteria.getTotalDailyAttendedCalls() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalDailyAttendedCalls(), DailyCalls_.totalDailyAttendedCalls));
            }
            if (criteria.getTotalDailyMissedCalls() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalDailyMissedCalls(), DailyCalls_.totalDailyMissedCalls));
            }
            if (criteria.getTotalDailyReceivedCallsExternalAgent() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getTotalDailyReceivedCallsExternalAgent(),
                            DailyCalls_.totalDailyReceivedCallsExternalAgent
                        )
                    );
            }
            if (criteria.getTotalDailyAttendedCallsExternalAgent() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getTotalDailyAttendedCallsExternalAgent(),
                            DailyCalls_.totalDailyAttendedCallsExternalAgent
                        )
                    );
            }
            if (criteria.getTotalDailyReceivedCallsInternalAgent() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getTotalDailyReceivedCallsInternalAgent(),
                            DailyCalls_.totalDailyReceivedCallsInternalAgent
                        )
                    );
            }
            if (criteria.getTotalDailyAttendedCallsInternalAgent() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getTotalDailyAttendedCallsInternalAgent(),
                            DailyCalls_.totalDailyAttendedCallsInternalAgent
                        )
                    );
            }
            if (criteria.getTotalDailyCallsTimeInMin() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getTotalDailyCallsTimeInMin(), DailyCalls_.totalDailyCallsTimeInMin)
                    );
            }
            if (criteria.getTotalDailyCallsTimeExternalAgentInMin() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getTotalDailyCallsTimeExternalAgentInMin(),
                            DailyCalls_.totalDailyCallsTimeExternalAgentInMin
                        )
                    );
            }
            if (criteria.getTotalDailyCallsTimeInternalAgentInMin() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getTotalDailyCallsTimeInternalAgentInMin(),
                            DailyCalls_.totalDailyCallsTimeInternalAgentInMin
                        )
                    );
            }
            if (criteria.getAvgDailyOperationTimeExternalAgentInMin() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getAvgDailyOperationTimeExternalAgentInMin(),
                            DailyCalls_.avgDailyOperationTimeExternalAgentInMin
                        )
                    );
            }
            if (criteria.getAvgDailyOperationTimeInternalAgentInMin() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getAvgDailyOperationTimeInternalAgentInMin(),
                            DailyCalls_.avgDailyOperationTimeInternalAgentInMin
                        )
                    );
            }
        }
        return specification;
    }
}
