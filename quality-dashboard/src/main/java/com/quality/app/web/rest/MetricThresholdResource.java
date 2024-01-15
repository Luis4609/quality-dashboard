package com.quality.app.web.rest;

import com.quality.app.repository.MetricThresholdRepository;
import com.quality.app.service.MetricThresholdQueryService;
import com.quality.app.service.MetricThresholdService;
import com.quality.app.service.criteria.MetricThresholdCriteria;
import com.quality.app.service.dto.MetricThresholdDTO;
import com.quality.app.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.quality.app.domain.MetricThreshold}.
 */
@RestController
@RequestMapping("/api/metric-thresholds")
public class MetricThresholdResource {

    private final Logger log = LoggerFactory.getLogger(MetricThresholdResource.class);

    private static final String ENTITY_NAME = "metricThreshold";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetricThresholdService metricThresholdService;

    private final MetricThresholdRepository metricThresholdRepository;

    private final MetricThresholdQueryService metricThresholdQueryService;

    public MetricThresholdResource(
        MetricThresholdService metricThresholdService,
        MetricThresholdRepository metricThresholdRepository,
        MetricThresholdQueryService metricThresholdQueryService
    ) {
        this.metricThresholdService = metricThresholdService;
        this.metricThresholdRepository = metricThresholdRepository;
        this.metricThresholdQueryService = metricThresholdQueryService;
    }

    /**
     * {@code POST  /metric-thresholds} : Create a new metricThreshold.
     *
     * @param metricThresholdDTO the metricThresholdDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metricThresholdDTO, or with status {@code 400 (Bad Request)} if the metricThreshold has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MetricThresholdDTO> createMetricThreshold(@Valid @RequestBody MetricThresholdDTO metricThresholdDTO)
        throws URISyntaxException {
        log.debug("REST request to save MetricThreshold : {}", metricThresholdDTO);
        if (metricThresholdDTO.getId() != null) {
            throw new BadRequestAlertException("A new metricThreshold cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetricThresholdDTO result = metricThresholdService.save(metricThresholdDTO);
        return ResponseEntity
            .created(new URI("/api/metric-thresholds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /metric-thresholds/:id} : Updates an existing metricThreshold.
     *
     * @param id the id of the metricThresholdDTO to save.
     * @param metricThresholdDTO the metricThresholdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metricThresholdDTO,
     * or with status {@code 400 (Bad Request)} if the metricThresholdDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metricThresholdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MetricThresholdDTO> updateMetricThreshold(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MetricThresholdDTO metricThresholdDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MetricThreshold : {}, {}", id, metricThresholdDTO);
        if (metricThresholdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metricThresholdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metricThresholdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetricThresholdDTO result = metricThresholdService.update(metricThresholdDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, metricThresholdDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /metric-thresholds/:id} : Partial updates given fields of an existing metricThreshold, field will ignore if it is null
     *
     * @param id the id of the metricThresholdDTO to save.
     * @param metricThresholdDTO the metricThresholdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metricThresholdDTO,
     * or with status {@code 400 (Bad Request)} if the metricThresholdDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metricThresholdDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metricThresholdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MetricThresholdDTO> partialUpdateMetricThreshold(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MetricThresholdDTO metricThresholdDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MetricThreshold partially : {}, {}", id, metricThresholdDTO);
        if (metricThresholdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metricThresholdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metricThresholdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetricThresholdDTO> result = metricThresholdService.partialUpdate(metricThresholdDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, metricThresholdDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /metric-thresholds} : get all the metricThresholds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metricThresholds in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MetricThresholdDTO>> getAllMetricThresholds(
        MetricThresholdCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MetricThresholds by criteria: {}", criteria);

        Page<MetricThresholdDTO> page = metricThresholdQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /metric-thresholds/count} : count all the metricThresholds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMetricThresholds(MetricThresholdCriteria criteria) {
        log.debug("REST request to count MetricThresholds by criteria: {}", criteria);
        return ResponseEntity.ok().body(metricThresholdQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /metric-thresholds/:id} : get the "id" metricThreshold.
     *
     * @param id the id of the metricThresholdDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metricThresholdDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MetricThresholdDTO> getMetricThreshold(@PathVariable Long id) {
        log.debug("REST request to get MetricThreshold : {}", id);
        Optional<MetricThresholdDTO> metricThresholdDTO = metricThresholdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metricThresholdDTO);
    }

    /**
     * {@code DELETE  /metric-thresholds/:id} : delete the "id" metricThreshold.
     *
     * @param id the id of the metricThresholdDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetricThreshold(@PathVariable Long id) {
        log.debug("REST request to delete MetricThreshold : {}", id);
        metricThresholdService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
