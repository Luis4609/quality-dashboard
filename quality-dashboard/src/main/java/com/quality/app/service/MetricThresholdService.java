package com.quality.app.service;

import com.quality.app.domain.MetricThreshold;
import com.quality.app.repository.MetricThresholdRepository;
import com.quality.app.service.dto.MetricThresholdDTO;
import com.quality.app.service.mapper.MetricThresholdMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.quality.app.domain.MetricThreshold}.
 */
@Service
@Transactional
public class MetricThresholdService {

    private final Logger log = LoggerFactory.getLogger(MetricThresholdService.class);

    private final MetricThresholdRepository metricThresholdRepository;

    private final MetricThresholdMapper metricThresholdMapper;

    public MetricThresholdService(MetricThresholdRepository metricThresholdRepository, MetricThresholdMapper metricThresholdMapper) {
        this.metricThresholdRepository = metricThresholdRepository;
        this.metricThresholdMapper = metricThresholdMapper;
    }

    /**
     * Save a metricThreshold.
     *
     * @param metricThresholdDTO the entity to save.
     * @return the persisted entity.
     */
    public MetricThresholdDTO save(MetricThresholdDTO metricThresholdDTO) {
        log.debug("Request to save MetricThreshold : {}", metricThresholdDTO);
        MetricThreshold metricThreshold = metricThresholdMapper.toEntity(metricThresholdDTO);
        metricThreshold = metricThresholdRepository.save(metricThreshold);
        return metricThresholdMapper.toDto(metricThreshold);
    }

    /**
     * Update a metricThreshold.
     *
     * @param metricThresholdDTO the entity to save.
     * @return the persisted entity.
     */
    public MetricThresholdDTO update(MetricThresholdDTO metricThresholdDTO) {
        log.debug("Request to update MetricThreshold : {}", metricThresholdDTO);
        MetricThreshold metricThreshold = metricThresholdMapper.toEntity(metricThresholdDTO);
        metricThreshold = metricThresholdRepository.save(metricThreshold);
        return metricThresholdMapper.toDto(metricThreshold);
    }

    /**
     * Partially update a metricThreshold.
     *
     * @param metricThresholdDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MetricThresholdDTO> partialUpdate(MetricThresholdDTO metricThresholdDTO) {
        log.debug("Request to partially update MetricThreshold : {}", metricThresholdDTO);

        return metricThresholdRepository
            .findById(metricThresholdDTO.getId())
            .map(existingMetricThreshold -> {
                metricThresholdMapper.partialUpdate(existingMetricThreshold, metricThresholdDTO);

                return existingMetricThreshold;
            })
            .map(metricThresholdRepository::save)
            .map(metricThresholdMapper::toDto);
    }

    /**
     * Get all the metricThresholds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MetricThresholdDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MetricThresholds");
        return metricThresholdRepository.findAll(pageable).map(metricThresholdMapper::toDto);
    }

    /**
     * Get one metricThreshold by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MetricThresholdDTO> findOne(Long id) {
        log.debug("Request to get MetricThreshold : {}", id);
        return metricThresholdRepository.findById(id).map(metricThresholdMapper::toDto);
    }

    /**
     * Delete the metricThreshold by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MetricThreshold : {}", id);
        metricThresholdRepository.deleteById(id);
    }
}
