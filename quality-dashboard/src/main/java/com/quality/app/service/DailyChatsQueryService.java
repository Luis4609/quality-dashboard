package com.quality.app.service;

import com.quality.app.domain.*; // for static metamodels
import com.quality.app.domain.DailyChats;
import com.quality.app.repository.DailyChatsRepository;
import com.quality.app.service.criteria.DailyChatsCriteria;
import com.quality.app.service.dto.DailyChatsDTO;
import com.quality.app.service.mapper.DailyChatsMapper;
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
 * Service for executing complex queries for {@link DailyChats} entities in the database.
 * The main input is a {@link DailyChatsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DailyChatsDTO} or a {@link Page} of {@link DailyChatsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DailyChatsQueryService extends QueryService<DailyChats> {

    private final Logger log = LoggerFactory.getLogger(DailyChatsQueryService.class);

    private final DailyChatsRepository dailyChatsRepository;

    private final DailyChatsMapper dailyChatsMapper;

    public DailyChatsQueryService(DailyChatsRepository dailyChatsRepository, DailyChatsMapper dailyChatsMapper) {
        this.dailyChatsRepository = dailyChatsRepository;
        this.dailyChatsMapper = dailyChatsMapper;
    }

    /**
     * Return a {@link List} of {@link DailyChatsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DailyChatsDTO> findByCriteria(DailyChatsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DailyChats> specification = createSpecification(criteria);
        return dailyChatsMapper.toDto(dailyChatsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DailyChatsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DailyChatsDTO> findByCriteria(DailyChatsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DailyChats> specification = createSpecification(criteria);
        return dailyChatsRepository.findAll(specification, page).map(dailyChatsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DailyChatsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DailyChats> specification = createSpecification(criteria);
        return dailyChatsRepository.count(specification);
    }

    /**
     * Function to convert {@link DailyChatsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DailyChats> createSpecification(DailyChatsCriteria criteria) {
        Specification<DailyChats> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DailyChats_.id));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDay(), DailyChats_.day));
            }
            if (criteria.getTotalDailyReceivedChats() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalDailyReceivedChats(), DailyChats_.totalDailyReceivedChats));
            }
            if (criteria.getTotalDailyConversationChatsTimeInMin() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getTotalDailyConversationChatsTimeInMin(),
                            DailyChats_.totalDailyConversationChatsTimeInMin
                        )
                    );
            }
            if (criteria.getTotalDailyAttendedChats() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalDailyAttendedChats(), DailyChats_.totalDailyAttendedChats));
            }
            if (criteria.getTotalDailyMissedChats() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalDailyMissedChats(), DailyChats_.totalDailyMissedChats));
            }
            if (criteria.getAvgDailyConversationChatsTimeInMin() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getAvgDailyConversationChatsTimeInMin(),
                            DailyChats_.avgDailyConversationChatsTimeInMin
                        )
                    );
            }
        }
        return specification;
    }
}
